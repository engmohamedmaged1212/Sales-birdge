package com.qvc.orderflow.repositories;


import com.qvc.orderflow.entities.PriceAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceAdjustmentRepository extends JpaRepository<PriceAdjustment,Long> {
}
