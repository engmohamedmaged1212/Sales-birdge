package com.qvc.orderflow.Prouduct.Product_Variants.dtos;

import com.qvc.orderflow.Prouduct.dtos.Create_Product.CreateVariantRequest;
import lombok.Data;

import java.util.List;

@Data
public class VariantRequest {
    private Long productId;
    private List<CreateVariantRequest> createVariantRequest;
}
