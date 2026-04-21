package com.qvc.orderflow.Prouduct.Product_Variants.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class Variant {
    private Long variantId;
    private String variantLabel;
    private String sku;
    private BigDecimal price;
    private Integer stock;
    private Boolean isActive;
    private Map<String, String> attributes;
}
