package com.qvc.orderflow.Prouduct.Main_Products;

import com.qvc.orderflow.Prouduct.Product_Variants.dtos.UpdateVariantRequest;
import com.qvc.orderflow.Prouduct.Product_Variants.dtos.VariantRequest;
import com.qvc.orderflow.Prouduct.Main_Products.dtos.Create_Product.CreateProductRequest;
import com.qvc.orderflow.Prouduct.Main_Products.dtos.ProductResponse;
import com.qvc.orderflow.Prouduct.Main_Products.dtos.SearchRequest;
import com.qvc.orderflow.Prouduct.Product_Variants.dtos.VariantResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServices productServices;

    // POST /products
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody @Valid CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productServices.createProduct(request));
    }

    // PUT /products/addVariants
    @PutMapping("/addVariants")
    public ResponseEntity<ProductResponse> addVariants(
            @RequestBody @Valid VariantRequest request) {
        return ResponseEntity.ok(productServices.addVariantsToProduct(request));
    }

    // GET /products
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productServices.getAllProducts());
    }

    // GET /products/{id}
    // GET /products/{id}?color=Red
    // GET /products/{id}?color=Red&size=M
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable Long productId,
            @RequestParam(required = false) Map<String, String> filters) {

        SearchRequest request = new SearchRequest();
        request.setProductId(productId);
        request.setFilters(filters);

        return ResponseEntity.ok(productServices.searchProduct(request));
    }

    @PutMapping("update")
    public ResponseEntity<VariantResponse> updateProduct(@RequestBody @Valid    UpdateVariantRequest request){
        return ResponseEntity.ok(productServices.updateVariant(request));
    }
}