package com.arka.usermcsv.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
  private Long supplierId;
  private String companyName;
  private String companyAddress;
  private String companyEmail;
  private Long userId;
}
