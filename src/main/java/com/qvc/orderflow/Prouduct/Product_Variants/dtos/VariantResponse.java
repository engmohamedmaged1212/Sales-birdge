package com.qvc.orderflow.Prouduct.Product_Variants.dtos;

import lombok.Data;

import java.util.List;

@Data
public class VariantResponse {
    List<Variant> variantList;
}
