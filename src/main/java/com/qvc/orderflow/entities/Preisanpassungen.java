package com.qvc.orderflow.entities;

import com.qvc.orderflow.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "preisanpassungen")
public class Preisanpassungen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anpassung_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "alter_preis")
    private BigDecimal alterPreis;

    @Column(name = "neuer_preis")
    private BigDecimal neuerPreis;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "geaendert_von")
    private User geaendertVon;

    @Column(name = "beschreibung", nullable = false)
    private String beschreibung;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "geaendert_am")
    private Instant geaendertAm;

}