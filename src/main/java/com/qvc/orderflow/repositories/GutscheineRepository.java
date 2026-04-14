package com.qvc.orderflow.repositories;

import com.qvc.orderflow.entities.Gutscheine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GutscheineRepository extends JpaRepository<Gutscheine,Long> {
}
