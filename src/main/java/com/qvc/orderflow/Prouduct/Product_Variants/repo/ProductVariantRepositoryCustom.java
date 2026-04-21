package com.qvc.orderflow.Prouduct.Product_Variants.repo;

import com.qvc.orderflow.Prouduct.Product_Variants.ProductVariant;

import java.util.List;
import java.util.Map;

public interface ProductVariantRepositoryCustom {
    List<ProductVariant> searchVariants(Long productId, Map<String, String> filters);
}
