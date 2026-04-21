package com.qvc.orderflow.entities;

import com.qvc.orderflow.Address.Address;
import com.qvc.orderflow.Coustomer.Customer;
import com.qvc.orderflow.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "orders")
@NoArgsConstructor @AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private User agent;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_address_id", nullable = false)
    private Address billingAddress;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id", nullable = false)
    private Address shippingAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status = OrderStatus.new_;

    @NotNull
    @Column(name = "total_gross", nullable = false)
    private BigDecimal totalGross = BigDecimal.ZERO;

    @NotNull
    @Column(name = "discount_amount", nullable = false)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @NotNull
    @Column(name = "final_amount", nullable = false)
    private BigDecimal finalAmount = BigDecimal.ZERO;

    @Lob
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "order_date", insertable = false, updatable = false)
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

}