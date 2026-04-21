package com.qvc.orderflow.Prouduct.Product_Variants.repo;

import com.qvc.orderflow.Prouduct.Product_Variants.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    List<ProductVariant> findAllByProduct_Id(Long productId);
}
