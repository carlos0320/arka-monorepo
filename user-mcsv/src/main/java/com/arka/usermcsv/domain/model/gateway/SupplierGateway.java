package com.arka.usermcsv.domain.model.gateway;

import com.arka.usermcsv.domain.model.Supplier;

import java.util.Optional;

public interface SupplierGateway {
  Supplier save(Supplier supplier);
  Optional<Supplier> findByName(String name);
  Optional<Supplier> findById(Long id);
  Optional<Supplier> findByUserId(Long userId);
}
