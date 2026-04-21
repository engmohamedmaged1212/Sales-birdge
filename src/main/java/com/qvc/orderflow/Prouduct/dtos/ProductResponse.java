package com.qvc.orderflow.Prouduct.dtos;

import com.qvc.orderflow.Prouduct.Product_Variants.dtos.VariantResponse;
import com.qvc.orderflow.Prouduct.Product_attributes_def.dtos.Attribute_Response.AttributesResponse;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductResponse {
    private Long productId;
    private String productName;
    private String description;
    private String size;
    private Boolean isElectrical;
    private String availability;
    private LocalDateTime createdAt;
    private AttributesResponse attributes;
    private VariantResponse variants;
}