package com.arka.usermcsv.infrastructure.adapter;

import com.arka.usermcsv.infrastructure.mapper.SupplierMapper;
import com.arka.usermcsv.domain.model.Supplier;
import com.arka.usermcsv.domain.model.gateway.SupplierGateway;
import com.arka.usermcsv.infrastructure.entity.SupplierEntity;
import com.arka.usermcsv.infrastructure.repository.SupplierRepository;

import java.util.Optional;

public class SupplierGatewayImpl implements SupplierGateway {
  private final SupplierRepository supplierRepository;
  private final SupplierMapper supplierMapper;

  public SupplierGatewayImpl(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
    this.supplierRepository = supplierRepository;
    this.supplierMapper = supplierMapper;
  }

  @Override
  public Supplier save(Supplier supplier) {
    SupplierEntity entity = supplierMapper.toEntity(supplier);
    return supplierMapper.toDomain(supplierRepository.save(entity));
  }

  @Override
  public Optional<Supplier> findByName(String name) {
    return supplierRepository.findByCompanyNameIgnoreCase(name)
            .map(entity -> supplierMapper.toDomain(entity));
  }

  @Override
  public Optional<Supplier> findById(Long id) {
    return Optional.empty();
  }

  @Override
  public Optional<Supplier> findByUserId(Long userId) {
    return Optional.empty();
  }
}
