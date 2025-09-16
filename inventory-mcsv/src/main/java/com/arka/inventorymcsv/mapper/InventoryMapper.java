package com.arka.inventorymcsv.mapper;

import com.arka.inventorymcsv.dto.InventoryDto;
import com.arka.inventorymcsv.entity.Inventory;

public class InventoryMapper {

   public static Inventory toInventory(InventoryDto inventoryDto) {
      Inventory inventory = new Inventory();


      inventory.setInventoryId(inventoryDto.getInventoryId());
      inventory.setStock(inventoryDto.getStock());
      inventory.setMinStock(inventoryDto.getMinStock());
      inventory.setAvailableStock(inventoryDto.getAvailableStock());
      inventory.setReservedStock(inventoryDto.getReservedStock());
      inventory.setProductId(inventoryDto.getProductId());
      return inventory;
   }

   public static InventoryDto toInventoryDto(Inventory inventory) {
      InventoryDto inventoryDto = new InventoryDto();

      inventoryDto.setStock(inventory.getStock());
      inventoryDto.setMinStock(inventory.getMinStock());
      inventoryDto.setReservedStock(inventory.getReservedStock());
      inventoryDto.setProductId(inventory.getProductId());
      inventoryDto.setInventoryId(inventory.getInventoryId());
      inventoryDto.setAvailableStock(inventory.getAvailableStock());

      return inventoryDto;
   }
}
