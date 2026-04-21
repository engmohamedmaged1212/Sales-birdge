package com.qvc.orderflow.entities;

import com.qvc.orderflow.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table(name = "coupons")
@NoArgsConstructor @AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    @NotNull
    @Column(name = "discount_amount", nullable = false)
    private BigDecimal discountAmount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @NotNull
    @Column(name = "valid_until", nullable = false)
    private LocalDate validUntil;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}