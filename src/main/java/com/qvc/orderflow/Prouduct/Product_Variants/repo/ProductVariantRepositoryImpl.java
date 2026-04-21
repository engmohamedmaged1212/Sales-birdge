package com.qvc.orderflow.Prouduct.Product_Variants.repo;

import com.qvc.orderflow.Prouduct.Product_Variants.ProductVariant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProductVariantRepositoryImpl implements ProductVariantRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<ProductVariant> searchVariants(Long productId, Map<String, String> filters) {

        // ── Step 1: find variant IDs that match ALL filters ──────────────
        // Strategy: for each variant, count how many of the requested
        // (attributeKey, attributeValue) pairs it has.
        // If count == filters.size() → it matches all filters.

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> idQuery = cb.createQuery(Long.class);
        Root<ProductVariant> variant = idQuery.from(ProductVariant.class);
        Join<Object, Object> attr = variant.join("attributes", JoinType.INNER);
        Join<Object, Object> def  = attr.join("definition",   JoinType.INNER);

        // Base predicate: correct product
        Predicate isProduct = cb.equal(variant.get("product").get("id"), productId);

        // Match any of the requested (key, value) pairs
        List<Predicate> orPredicates = new ArrayList<>();
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            orPredicates.add(cb.and(
                    cb.equal(def.get("attributeKey"),    entry.getKey()),
                    cb.equal(attr.get("attributeValue"), entry.getValue())
            ));
        }

        idQuery.select(variant.get("id"))
                .where(cb.and(
                        isProduct,
                        cb.or(orPredicates.toArray(new Predicate[0]))
                ))
                .groupBy(variant.get("id"))
                // count distinct matched attributes — must equal filters.size()
                .having(cb.equal(cb.countDistinct(attr.get("id")), (long) filters.size()));

        List<Long> matchingIds = em.createQuery(idQuery).getResultList();

        if (matchingIds.isEmpty()) {
            return List.of();
        }

        // ── Step 2: fetch full entities by IDs (with attributes eagerly) ─
        CriteriaQuery<ProductVariant> fetchQuery = cb.createQuery(ProductVariant.class);
        Root<ProductVariant> fetchRoot = fetchQuery.from(ProductVariant.class);

        // Fetch attributes and their definitions in one query — avoids N+1
        fetchRoot.fetch("attributes", JoinType.LEFT)
                .fetch("definition", JoinType.LEFT);

        fetchQuery.select(fetchRoot)
                .distinct(true)
                .where(fetchRoot.get("id").in(matchingIds));

        return em.createQuery(fetchQuery).getResultList();
    }
}