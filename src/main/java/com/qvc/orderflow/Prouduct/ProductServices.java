package com.qvc.orderflow.Prouduct;

import com.qvc.orderflow.Prouduct.Product_Variants.ProductVariantServices;
import com.qvc.orderflow.Prouduct.Product_Variants.dtos.GetAllVariantsRequest;
import com.qvc.orderflow.Prouduct.Product_Variants.dtos.VariantRequest;
import com.qvc.orderflow.Prouduct.Product_Variants.repo.ProductVariantRepositoryCustom;
import com.qvc.orderflow.Prouduct.Product_attributes_def.VariantAttributeDefinitionServices;
import com.qvc.orderflow.Prouduct.Product_attributes_def.dtos.GetAttributesForProductDto;
import com.qvc.orderflow.Prouduct.Product_attributes_def.dtos.InsertAttributesRequests;
import com.qvc.orderflow.Prouduct.dtos.Create_Product.CreateProductRequest;
import com.qvc.orderflow.Prouduct.dtos.ProductResponse;
import com.qvc.orderflow.Prouduct.dtos.SearchRequest;
import com.qvc.orderflow.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServices {

    private final ProductRepository productRepository;
    private final VariantAttributeDefinitionServices     attributeDefinitionServices;
    private final ProductVariantServices  variantServices;
    private final ProductVariantRepositoryCustom productVariantRepositoryCustom;

    // ─────────────────────────────────────────────
    //  CREATE
    // ─────────────────────────────────────────────
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {

        // 1. Save the base product
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        product.setSize(request.getProductSize() != null
                ? request.getProductSize() : Product.ProductSize.normal);
        product.setIsElectrical(request.isElectrical());
        product.setAvailability(Product.Availability.available);
        productRepository.save(product);

        // 2. Save attribute definitions if provided
        if (request.getAttributeDefinitions() != null && !request.getAttributeDefinitions().isEmpty()) {
            InsertAttributesRequests attrRequest = new InsertAttributesRequests();
            attrRequest.setProductId(product.getId());
            attrRequest.setRequestAttributes(request.getAttributeDefinitions());
            attributeDefinitionServices.addAttributes(attrRequest);
        }

        // 3. Save variants if provided
        if (request.getVariantRequests() != null && !request.getVariantRequests().isEmpty()) {
            VariantRequest variantRequest = new VariantRequest();
            variantRequest.setProductId(product.getId());
            variantRequest.setCreateVariantRequest(request.getVariantRequests());
            variantServices.createVariants(variantRequest);
        }

        return buildResponse(product.getId());
    }

    // ─────────────────────────────────────────────
    //  GET ALL
    // ─────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(p -> buildResponse(p.getId()))
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────
    //  SEARCH
    //  GET /api/products/{id}?color=Red&size=M
    //  filters is empty  -> return all variants
    //  filters has values -> return matching variants only
    // ─────────────────────────────────────────────
    @Transactional(readOnly = true)
    public ProductResponse searchProduct(SearchRequest request) {

        ProductResponse response = buildResponse(request.getProductId());

        if (request.getFilters() != null && !request.getFilters().isEmpty()) {
            response.setVariants(variantServices.searchVariants(request));
        }
        return response;
    }

    //  INTERNAL: used by other services
    public Product getProductByIdForAnotherServices(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));
    }

    public ProductResponse addVariantsToProduct(VariantRequest request){
        var p = this.getProductByIdForAnotherServices(request.getProductId());
        variantServices.createVariants(request);
        return buildResponse(p.getId());
    }

    //  PRIVATE: assemble full ProductResponse
    private ProductResponse buildResponse(Long productId) {

        Product product = getProductByIdForAnotherServices(productId);

        GetAttributesForProductDto attrDto = new GetAttributesForProductDto();
        attrDto.setProductId(productId);

        GetAllVariantsRequest variantDto = new GetAllVariantsRequest();
        variantDto.setProductId(productId);

        ProductResponse response = new ProductResponse();
        response.setProductId(product.getId());
        response.setProductName(product.getProductName());
        response.setDescription(product.getDescription());
        response.setSize(product.getSize() != null ? product.getSize().name() : null);
        response.setIsElectrical(product.getIsElectrical());
        response.setAvailability(product.getAvailability() != null
                ? product.getAvailability().name() : null);
        response.setCreatedAt(product.getCreatedAt());
        response.setAttributes(attributeDefinitionServices.getAttributes(attrDto));
        response.setVariants(variantServices.getAllVariants(variantDto));

        return response;
    }
}