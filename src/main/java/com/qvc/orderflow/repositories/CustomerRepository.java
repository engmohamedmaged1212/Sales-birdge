package com.qvc.orderflow.repositories;

import com.qvc.orderflow.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
