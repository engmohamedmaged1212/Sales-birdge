package com.qvc.orderflow.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "aktionen")
public class Aktionen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aktion_id")
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "rabatt_prozent")
    private BigDecimal rabattProzent;

    @Column(name = "rabatt_betrag")
    private BigDecimal rabattBetrag;

    @Column(name = "aktionspreis")
    private BigDecimal aktionspreis;

    @Column(name = "gueltig_von")
    private LocalDate gueltigVon;

    @Column(name = "gueltig_bis")
    private LocalDate gueltigBis;

    @Column(name = "ist_aktiv")
    private Boolean istAktiv = false;

}