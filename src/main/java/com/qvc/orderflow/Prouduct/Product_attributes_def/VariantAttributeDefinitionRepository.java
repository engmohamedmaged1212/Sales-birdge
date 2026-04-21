package com.qvc.orderflow.Prouduct.Product_attributes_def;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantAttributeDefinitionRepository  extends JpaRepository<VariantAttributeDefinition,Long> {
    List<VariantAttributeDefinition> findByProduct_Id(Long productId);
}
