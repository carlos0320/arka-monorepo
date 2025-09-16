package com.arka.inventorymcsv.service;

import com.arka.inventorymcsv.dto.InventoryDto;
import com.arka.inventorymcsv.entity.Inventory;

import java.util.List;

public interface IInventoryService {
   void createInventory(InventoryDto inventoryDto);
   List<InventoryDto> fetchAll();
   InventoryDto inventoryDetails(String productId);
   void updateInventory(String productId, InventoryDto inventoryDto);
}
