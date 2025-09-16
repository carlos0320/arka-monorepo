package com.arka.usermcsv.mappers;

import com.arka.usermcsv.dtos.SupplierDto;
import com.arka.usermcsv.entities.Supplier;

public class SupplierMapper {

   public static Supplier toSupplier(SupplierDto supplierDto){
      Supplier supplier = new Supplier();
      supplier.setCompanyAddress(supplierDto.getCompanyAddress());
      supplier.setCompanyName(supplierDto.getCompanyName());
      return supplier;
   }

   public static SupplierDto toSupplierDto(Supplier supplier){
      SupplierDto supplierDto = new SupplierDto();
      supplierDto.setCompanyAddress(supplier.getCompanyAddress());
      supplierDto.setCompanyName(supplier.getCompanyName());
      return supplierDto;
   }
}
