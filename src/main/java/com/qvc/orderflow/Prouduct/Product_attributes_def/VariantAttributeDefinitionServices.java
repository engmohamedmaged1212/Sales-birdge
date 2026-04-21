package com.qvc.orderflow.Prouduct.Product_attributes_def;

import com.qvc.orderflow.Prouduct.Product;
import com.qvc.orderflow.Prouduct.ProductRepository;
import com.qvc.orderflow.Prouduct.Product_attributes_def.dtos.Attribute_Response.Attribute;
import com.qvc.orderflow.Prouduct.Product_attributes_def.dtos.Attribute_Response.AttributesResponse;
import com.qvc.orderflow.Prouduct.Product_attributes_def.dtos.GetAttributesForProductDto;
import com.qvc.orderflow.Prouduct.Product_attributes_def.dtos.InsertAttributesRequests;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariantAttributeDefinitionServices {

    private final VariantAttributeDefinitionRepository variantAttributeDefinitionRepository;
    private final ProductRepository                    productRepository; // direct — no cycle

    public AttributesResponse getAttributes(GetAttributesForProductDto request) {
        ensureProductExists(request.getProductId());

        List<Attribute> attributes = variantAttributeDefinitionRepository
                .findByProduct_Id(request.getProductId())
                .stream()
                .map(def -> {
                    Attribute attr = new Attribute();
                    attr.setId(def.getId());
                    attr.setAttributeKey(def.getAttributeKey());
                    attr.setDisplayName(def.getDisplayName());
                    attr.setSortOrder(def.getSortOrder());
                    return attr;
                })
                .toList();

        AttributesResponse response = new AttributesResponse();
        response.setAttributes(attributes);
        return response;
    }

    public AttributesResponse addAttributes(InsertAttributesRequests request) {
        Product product = ensureProductExists(request.getProductId());

        List<VariantAttributeDefinition> definitions = request.getRequestAttributes()
                .stream()
                .map(req -> {
                    VariantAttributeDefinition def = new VariantAttributeDefinition();
                    def.setProduct(product);
                    def.setAttributeKey(req.getAttributeKey());
                    def.setDisplayName(req.getDisplayName());
                    def.setSortOrder(req.getSortOrder());
                    return def;
                })
                .toList();

        variantAttributeDefinitionRepository.saveAll(definitions);

        GetAttributesForProductDto dto = new GetAttributesForProductDto();
        dto.setProductId(request.getProductId());
        return getAttributes(dto);
    }

    // ─────────────────────────────────────────────
    //  PRIVATE
    // ─────────────────────────────────────────────
    private Product ensureProductExists(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productId));
    }
}