package com.qvc.orderflow.Prouduct.Main_Products.dtos.Create_Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class CreateVariantRequest {

    @NotBlank
    private String variantLabel;

    @NotBlank
    private String sku;

    @NotNull(message = "price")
    @Positive
    private BigDecimal price;

    @Min(0)
    private int stock;

    // e.g. {"color": "Red", "size": "M"}
    private Map<String, String> attributes;
}