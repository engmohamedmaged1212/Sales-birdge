package com.qvc.orderflow.Prouduct;

import com.qvc.orderflow.Prouduct.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
