package com.arka.inventorymcsv.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
  private Long brandId;
  private String name;
  private String logo;
}
