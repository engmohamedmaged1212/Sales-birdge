package com.qvc.orderflow.Prouduct;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@Entity
@Table(name = "variant_attributes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"variant_id", "definition_id"}))
@NoArgsConstructor @AllArgsConstructor
public class VariantAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant variant;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "definition_id", nullable = false)
    private VariantAttributeDefinition definition;

    @NotNull
    @Column(name = "attribute_value", nullable = false, length = 100)
    private String attributeValue;
}