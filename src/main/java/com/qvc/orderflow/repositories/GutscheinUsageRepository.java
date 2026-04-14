package com.qvc.orderflow.repositories;

import com.qvc.orderflow.entities.GutscheinUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GutscheinUsageRepository extends JpaRepository<GutscheinUsage,Long> {
}
