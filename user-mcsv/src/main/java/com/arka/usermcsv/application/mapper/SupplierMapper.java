package com.arka.usermcsv.application.mapper;

import com.arka.usermcsv.application.dto.SupplierDto;
import com.arka.usermcsv.domain.model.Supplier;

public class SupplierMapper {
  public static Supplier toDomain(SupplierDto dto) {
    if (dto == null) return null;
    Supplier supplier = new Supplier();
    supplier.setCompanyName(dto.getCompanyName());
    supplier.setCompanyAddress(dto.getCompanyAddress());
    supplier.setCompanyEmail(dto.getCompanyEmail());
    return supplier;
  }

  public static SupplierDto toDto(Supplier supplier) {
    if (supplier == null) return null;
    SupplierDto dto = new SupplierDto();
    dto.setSupplierId(supplier.getSupplierId());
    dto.setCompanyName(supplier.getCompanyName());
    dto.setCompanyAddress(supplier.getCompanyAddress());
    dto.setCompanyEmail(supplier.getCompanyEmail());
    return dto;
  }
}
