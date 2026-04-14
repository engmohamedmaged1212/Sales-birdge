package com.qvc.orderflow.repositories;

import com.qvc.orderflow.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
