package com.arka.usermcsv.application.dto;

import lombok.Data;

@Data
public class SupplierDto {
  private Long supplierId;
  private String companyName;
  private String companyAddress;
  private String companyEmail;
}
