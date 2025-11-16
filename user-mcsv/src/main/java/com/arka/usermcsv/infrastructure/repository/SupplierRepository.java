package com.arka.usermcsv.infrastructure.repository;

import com.arka.usermcsv.infrastructure.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
  Optional<SupplierEntity> findByCompanyNameIgnoreCase(String companyName);
}
