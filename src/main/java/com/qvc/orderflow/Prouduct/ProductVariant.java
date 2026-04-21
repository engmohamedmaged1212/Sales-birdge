package com.qvc.orderflow.Prouduct;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "product_variants")
@NoArgsConstructor @AllArgsConstructor
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variant_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Column(name = "variant_label", nullable = false, length = 200)
    private String variantLabel;

    @NotNull
    @Column(name = "sku", nullable = false, unique = true, length = 100)
    private String sku;

    @Column(name = "price_override")
    private BigDecimal priceOverride;

    @Column(name = "stock", nullable = false)
    private Integer stock = 0;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VariantAttribute> attributes;

    public BigDecimal getEffectivePrice() {
        return priceOverride != null ? priceOverride : product.getBasePrice();
    }
}