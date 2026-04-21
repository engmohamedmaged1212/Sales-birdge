package com.qvc.orderflow.repositories;

import com.qvc.orderflow.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
}
