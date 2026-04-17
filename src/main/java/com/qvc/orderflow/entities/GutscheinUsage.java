package com.qvc.orderflow.entities;

import com.qvc.orderflow.Coustomer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "gutschein_usage")
public class GutscheinUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id")
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull
    @Column(name = "rabatt_betrag")
    private BigDecimal rabattBetrag;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "used_at")
    private LocalDateTime usedAt;

}