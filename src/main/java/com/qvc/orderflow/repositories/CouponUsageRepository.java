package com.qvc.orderflow.repositories;

import com.qvc.orderflow.entities.CouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponUsageRepository extends JpaRepository<CouponUsage,Long> {
}
