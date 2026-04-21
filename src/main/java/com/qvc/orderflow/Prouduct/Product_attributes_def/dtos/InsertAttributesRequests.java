package com.qvc.orderflow.Prouduct.Product_attributes_def.dtos;

import com.qvc.orderflow.Prouduct.dtos.Create_Product.AttributeDefinitionRequest;
import lombok.Data;


import java.util.List;

@Data
public class InsertAttributesRequests {
    private Long productId;
    private List<AttributeDefinitionRequest> requestAttributes;
}
