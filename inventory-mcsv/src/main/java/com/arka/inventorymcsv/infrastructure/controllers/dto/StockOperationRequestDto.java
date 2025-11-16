package com.arka.inventorymcsv.infrastructure.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockOperationRequestDto {
  private Long productId;
  private Integer quantity;
}
