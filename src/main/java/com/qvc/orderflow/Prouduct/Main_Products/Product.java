package com.qvc.orderflow.Prouduct.Main_Products;

import com.qvc.orderflow.Prouduct.Product_Variants.ProductVariant;
import com.qvc.orderflow.Prouduct.Product_attributes_def.VariantAttributeDefinition;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "products")
@NoArgsConstructor @AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @NotNull
    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "size", nullable = false)
    private ProductSize size = ProductSize.normal;

    @NotNull
    @Column(name = "is_electrical", nullable = false)
    private Boolean isElectrical = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "availability", nullable = false)
    private Availability availability = Availability.available;


    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<VariantAttributeDefinition> attributeDefinitions;

    public enum ProductSize    { normal, xxl, oepdl }
    public enum Availability   { available, waitlist, sold_out }
}