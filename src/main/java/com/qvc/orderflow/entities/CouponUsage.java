package com.qvc.orderflow.entities;

import com.qvc.orderflow.Coustomer.Customer;
import com.qvc.orderflow.entities.Order;
import com.qvc.orderflow.entities.Promotion;
import com.qvc.orderflow.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table(name = "coupon_usage")
@NoArgsConstructor @AllArgsConstructor
public class CouponUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agent_id", nullable = false)
    private User agent;

    @NotNull
    @Column(name = "discount_amount", nullable = false)
    private BigDecimal discountAmount;

    @Column(name = "used_at", insertable = false, updatable = false)
    private LocalDateTime usedAt;
}