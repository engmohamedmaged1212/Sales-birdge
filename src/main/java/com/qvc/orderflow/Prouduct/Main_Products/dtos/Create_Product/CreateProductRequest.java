package com.qvc.orderflow.Prouduct.Main_Products.dtos.Create_Product;

import com.qvc.orderflow.Prouduct.Main_Products.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CreateProductRequest {
    @NotBlank(message = "Product name is required")
    private String productName;
    private String description;
    private Product.ProductSize productSize;
    private boolean isElectrical ;

    ///  the attributes for this product if it exists (size , color , with battery or not , .... etc.)
    @Valid
    List<AttributeDefinitionRequest> attributeDefinitions;

    ///  the variants of this products
    @Valid
    List<CreateVariantRequest> variantRequests;
}
