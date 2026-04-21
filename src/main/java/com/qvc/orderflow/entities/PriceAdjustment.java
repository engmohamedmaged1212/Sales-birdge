package com.qvc.orderflow.entities;

import com.qvc.orderflow.Coustomer.Customer;
import com.qvc.orderflow.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table(name = "price_adjustments")
@NoArgsConstructor @AllArgsConstructor
public class PriceAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adjustment_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @Column(name = "old_price", nullable = false)
    private BigDecimal oldPrice;

    @NotNull
    @Column(name = "new_price", nullable = false)
    private BigDecimal newPrice;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "changed_by", nullable = false)
    private User changedBy;

    @NotNull
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "changed_at", insertable = false, updatable = false)
    private LocalDateTime changedAt;
}