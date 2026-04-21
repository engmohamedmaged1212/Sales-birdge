package com.qvc.orderflow.Prouduct.Product_attributes_def.dtos.Attribute_Response;

import lombok.Data;

@Data
public class Attribute {
    private Long id;
    private String attributeKey;
    private String  displayName;
    private int sortOrder;
}
