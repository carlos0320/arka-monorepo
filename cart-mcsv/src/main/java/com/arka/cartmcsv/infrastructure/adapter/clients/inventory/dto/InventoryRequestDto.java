package com.arka.cartmcsv.infrastructure.adapter.clients.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequestDto {
  private Long productId;
  private Integer quantity;
  private List<Long> productIds;
}
