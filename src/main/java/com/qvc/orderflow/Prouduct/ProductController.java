package com.qvc.orderflow.Prouduct;

import com.qvc.orderflow.Prouduct.Product_Variants.repo.ProductVariantRepository;
import com.qvc.orderflow.Prouduct.dtos.Create_Product.CreateProductRequest;
import com.qvc.orderflow.Prouduct.dtos.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServices productServices;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid CreateProductRequest request) {
        var response = productServices.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
