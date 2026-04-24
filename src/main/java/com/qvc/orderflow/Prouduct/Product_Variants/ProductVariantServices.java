package com.qvc.orderflow.Prouduct.Product_Variants;

import com.qvc.orderflow.Prouduct.Main_Products.Product;
import com.qvc.orderflow.Prouduct.Main_Products.ProductRepository;
import com.qvc.orderflow.Prouduct.Variant_Attributes.VariantAttribute;
import com.qvc.orderflow.Prouduct.Variant_Attributes.VariantAttributeRepository;
import com.qvc.orderflow.Prouduct.Product_Variants.dtos.*;
import com.qvc.orderflow.Prouduct.Product_Variants.repo.ProductVariantRepository;
import com.qvc.orderflow.Prouduct.Product_Variants.repo.ProductVariantRepositoryCustom;
import com.qvc.orderflow.Prouduct.Product_attributes_def.VariantAttributeDefinition;
import com.qvc.orderflow.Prouduct.Product_attributes_def.VariantAttributeDefinitionRepository;
import com.qvc.orderflow.Prouduct.Main_Products.dtos.SearchRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductVariantServices {

    private final ProductVariantRepository             productVariantRepository;
    private final ProductRepository                    productRepository;
    private final VariantAttributeDefinitionRepository attributeDefinitionRepository;
    private final VariantAttributeRepository           variantAttributeRepository;
    private final ProductVariantRepositoryCustom       productVariantRepositoryCustom;

    // ─────────────────────────────────────────────
    //  GET ALL VARIANTS
    // ─────────────────────────────────────────────
    @Transactional(readOnly = true)
    public VariantResponse getAllVariants(GetAllVariantsRequest request) {
        ensureProductExists(request.getProductId());

        List<ProductVariant> variants =
                productVariantRepository.findAllByProduct_Id(request.getProductId());

        VariantResponse response = new VariantResponse();
        response.setVariantList(variants.stream()
                .map(this::mapToVariantDto)
                .toList());
        return response;
    }

    // ─────────────────────────────────────────────
    //  SEARCH VARIANTS BY FILTERS
    // ─────────────────────────────────────────────
    @Transactional(readOnly = true)
    public VariantResponse searchVariants(SearchRequest request) {
        ensureProductExists(request.getProductId());

        List<ProductVariant> variants =
                productVariantRepositoryCustom.searchVariants(request.getProductId(), request.getFilters());

        VariantResponse response = new VariantResponse();
        response.setVariantList(variants.stream()
                .map(this::mapToVariantDto)
                .toList());
        return response;
    }

    // ─────────────────────────────────────────────
    //  CREATE VARIANTS
    //  Fix: save variant first → then save its attributes separately
    //  This guarantees variant has an ID before attributes reference it
    // ─────────────────────────────────────────────
    @Transactional
    public VariantResponse createVariants(VariantRequest request) {
        Product product = ensureProductExists(request.getProductId());

        // Load all attribute definitions for this product keyed by attributeKey
        Map<String, VariantAttributeDefinition> definitionMap =
                attributeDefinitionRepository.findByProduct_Id(request.getProductId())
                        .stream()
                        .collect(Collectors.toMap(
                                VariantAttributeDefinition::getAttributeKey,
                                Function.identity()
                        ));

        for (var req : request.getCreateVariantRequest()) {

            // Step 1: save the variant first so it gets an ID
            ProductVariant variant = new ProductVariant();
            variant.setProduct(product);
            variant.setVariantLabel(req.getVariantLabel());
            variant.setSku(req.getSku());
            variant.setPrice(req.getPrice());
            variant.setStock(req.getStock());
            variant.setIsActive(true);
            productVariantRepository.save(variant); // flush to DB → variant now has ID

            // Step 2: save its attributes now that variant has an ID
            if (req.getAttributes() != null) {
                List<VariantAttribute> variantAttributes = new ArrayList<>();

                for (Map.Entry<String, String> entry : req.getAttributes().entrySet()) {
                    VariantAttributeDefinition def = definitionMap.get(entry.getKey());
                    if (def == null) {
                        throw new IllegalArgumentException(
                                "Attribute key '" + entry.getKey() + "' is not defined for this product.");
                    }
                    VariantAttribute va = new VariantAttribute();
                    va.setVariant(variant);
                    va.setDefinition(def);
                    va.setAttributeValue(entry.getValue());
                    variantAttributes.add(va);
                }

                variantAttributeRepository.saveAll(variantAttributes);
            }
        }

        GetAllVariantsRequest getAllRequest = new GetAllVariantsRequest();
        getAllRequest.setProductId(request.getProductId());
        return getAllVariants(getAllRequest);
    }

    // ─────────────────────────────────────────────
    //  PRIVATE
    // ─────────────────────────────────────────────
    private Product ensureProductExists(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productId));
    }

    private Variant mapToVariantDto(ProductVariant variant) {
        Variant dto = new Variant();
        dto.setVariantId(variant.getId());
        dto.setVariantLabel(variant.getVariantLabel());
        dto.setSku(variant.getSku());
        dto.setPrice(variant.getPrice());
        dto.setStock(variant.getStock());
        dto.setIsActive(variant.getIsActive());

        if (variant.getAttributes() != null) {
            Map<String, String> attributeMap = variant.getAttributes().stream()
                    .collect(Collectors.toMap(
                            va -> va.getDefinition().getAttributeKey(),
                            VariantAttribute::getAttributeValue
                    ));
            dto.setAttributes(attributeMap);
        }

        return dto;
    }
}