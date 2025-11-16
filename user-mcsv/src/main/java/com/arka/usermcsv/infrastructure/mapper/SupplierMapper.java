package com.arka.usermcsv.infrastructure.mapper;

import com.arka.usermcsv.domain.model.Supplier;
import com.arka.usermcsv.infrastructure.entity.SupplierEntity;
import com.arka.usermcsv.infrastructure.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class SupplierMapper {
  public SupplierEntity toEntity(Supplier supplier) {
    if (supplier == null) return null;
    SupplierEntity entity = new SupplierEntity();
    entity.setSupplierId(supplier.getSupplierId());
    entity.setCompanyName(supplier.getCompanyName());
    entity.setCompanyAddress(supplier.getCompanyAddress());
    entity.setCompanyEmail(supplier.getCompanyEmail());
    return entity;
  }

  public Supplier toDomain(SupplierEntity entity) {
    if (entity == null) return null;
    Supplier supplier = new Supplier();
    supplier.setSupplierId(entity.getSupplierId());
    supplier.setCompanyName(entity.getCompanyName());
    supplier.setCompanyAddress(entity.getCompanyAddress());
    supplier.setCompanyEmail(entity.getCompanyEmail());
    supplier.setUserId(entity.getUser().getUserId());
    return supplier;
  }
}
