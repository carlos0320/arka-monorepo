package com.arka.inventorymcsv.service.impl;

import com.arka.inventorymcsv.dto.InventoryDto;
import com.arka.inventorymcsv.entity.Inventory;
import com.arka.inventorymcsv.exception.ResourceAlreadyExistsException;
import com.arka.inventorymcsv.exception.ResourceNotFoundException;
import com.arka.inventorymcsv.mapper.InventoryMapper;
import com.arka.inventorymcsv.repository.InventoryRepository;
import com.arka.inventorymcsv.service.IInventoryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class InventoryServiceImpl implements IInventoryService {

   private final InventoryRepository inventoryRepository;

   @Override
   public void createInventory(InventoryDto inventoryDto) {

      Optional<Inventory> inventory = inventoryRepository.findByProductId(inventoryDto.getProductId());

      if (inventory.isPresent()){
         throw new ResourceAlreadyExistsException("Inventory already exists with this product id: " + inventoryDto.getProductId());
      }

      inventoryRepository.save(InventoryMapper.toInventory(inventoryDto));
   }

   @Override
   public List<InventoryDto> fetchAll() {
      return inventoryRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
              .stream()
              .map(InventoryMapper::toInventoryDto)
              .toList();
   }

   @Override
   public InventoryDto inventoryDetails(String productId) {
      Optional<Inventory> inventory = inventoryRepository.findByProductId(Long.valueOf(productId));
      if (inventory.isEmpty()){
         throw new ResourceNotFoundException("Inventory not found with this product id: " + productId);
      }
      return inventory.map(InventoryMapper::toInventoryDto).get();
   }

   @Override
   public void updateInventory(String productId, InventoryDto inventoryDto) {
      Inventory inventory = inventoryRepository.findByProductId(Long.valueOf(productId))
              .orElseGet(() -> {
                 throw new ResourceNotFoundException("Inventory not found with this product id: " + productId);
              });

      if (inventoryDto.getStock() != null){
         inventory.setStock(inventoryDto.getStock());
      }

      if (inventoryDto.getMinStock() != null){
         inventory.setMinStock(inventoryDto.getMinStock());
      }

      if(inventoryDto.getReservedStock() != null){
         inventory.setReservedStock(inventoryDto.getReservedStock());
      }

      inventoryRepository.save(inventory);
   }



}
