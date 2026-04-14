package com.qvc.orderflow.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rechnungsadresse_id")
    private Address rechnungsadresse;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lieferadresse_id")
    private Address lieferadresse;


    @Column(name = "zahlungsart", nullable = false)
    @Enumerated(EnumType.STRING)
    private Zahlungsart zahlungsart;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @NotNull
    @ColumnDefault("0.00")
    @Column(name = "gesamtbetrag_brutto")
    private BigDecimal gesamtbetragBrutto;

    @NotNull
    @ColumnDefault("0.00")
    @Column(name = "rabatt_betrag")
    private BigDecimal rabattBetrag;

    @NotNull
    @ColumnDefault("0.00")
    @Column(name = "endbetrag", nullable = false, precision = 10, scale = 2)
    private BigDecimal endbetrag;

    @Lob
    @Column(name = "notizen", columnDefinition = "TEXT")
    private String notizen;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

}