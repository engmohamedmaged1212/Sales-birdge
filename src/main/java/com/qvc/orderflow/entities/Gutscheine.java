package com.qvc.orderflow.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "gutscheine")
public class Gutscheine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gutschein_id", nullable = false)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "rabatt_betrag")
    private BigDecimal rabattBetrag;


    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;


    @Column(name = "gueltig_von", nullable = false)
    private LocalDate gueltigVon;


    @Column(name = "gueltig_bis", nullable = false)
    private LocalDate gueltigBis;


    @Column(name = "ist_aktiv", nullable = false)
    private Boolean istAktiv = false;

}