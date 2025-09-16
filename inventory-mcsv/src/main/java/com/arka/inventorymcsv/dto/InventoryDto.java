package com.arka.inventorymcsv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDto {
   private Long inventoryId;

   private Integer stock;

   private Integer minStock;

   private Integer reservedStock;

   private Integer availableStock;

   private Long productId;
}
