package com.qvc.orderflow.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;


    @Column(name = "produktname", nullable = false, length = 150)
    private String produktname;

    @Lob
    @Column(name = "beschreibung" , columnDefinition = "TEXT")
    private String beschreibung;

    @NotNull
    @Column(name = "groesse")
    @Enumerated(EnumType.STRING)
    private GrossesArtikel groesse;

    @NotNull
    @Column(name = "ist_elektrog")
    private Boolean istElektrog = false;


    @Column(name = "verfuegbarkeit", nullable = false)
    @Enumerated(EnumType.STRING)
    private Verfuegbarkeit verfuegbarkeit;

    @Column(name = "lagerbestand", nullable = false)
    private Integer lagerbestand;

    @NotNull
    @Column(name = "normalpreis")
    private BigDecimal normalpreis;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}