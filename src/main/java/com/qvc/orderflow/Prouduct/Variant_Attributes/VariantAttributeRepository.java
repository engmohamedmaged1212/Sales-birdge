package com.qvc.orderflow.Prouduct.Variant_Attributes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantAttributeRepository  extends JpaRepository<VariantAttribute,Long> {
}
