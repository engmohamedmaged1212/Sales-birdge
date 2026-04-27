package com.qvc.orderflow.Prouduct.Product_Variants.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateVariantRequest {
    @NotNull(message = "Variant ID is required")
    private Long variantId;

    @DecimalMin(value = "0.0", message = "Price cannot be less than zero")
    private BigDecimal price;

    @Min(value = 0, message = "Quantity cannot be less than zero")
    private Integer quantity;
}
