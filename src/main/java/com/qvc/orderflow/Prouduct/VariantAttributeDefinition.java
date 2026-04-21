package com.qvc.orderflow.Prouduct;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@Entity
@Table(name = "variant_attribute_definitions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "attribute_key"}))
@NoArgsConstructor @AllArgsConstructor
public class VariantAttributeDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "definition_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull
    @Column(name = "attribute_key", nullable = false, length = 50)
    private String attributeKey;

    @NotNull
    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;
}