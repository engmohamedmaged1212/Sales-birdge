package com.qvc.orderflow.repositories;

import com.qvc.orderflow.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
