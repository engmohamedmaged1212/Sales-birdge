package com.qvc.orderflow.Prouduct.Main_Products.dtos.Create_Product;

import lombok.Data;

@Data
public class AttributeDefinitionRequest {
    private String attributeKey ;
    private String displayName;
    private int sortOrder;
}
