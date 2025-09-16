package com.arka.inventorymcsv.controller;

import com.arka.inventorymcsv.constants.InventoryConstants;
import com.arka.inventorymcsv.dto.InventoryDto;
import com.arka.inventorymcsv.dto.ResponseDto;
import com.arka.inventorymcsv.service.IInventoryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/inventory")
public class InventoryController {

   private final IInventoryService inventoryService;

   @PostMapping
   public ResponseEntity<ResponseDto> createInventory(@RequestBody InventoryDto inventoryDto) {
      inventoryService.createInventory(inventoryDto);
      return ResponseEntity.ok(new ResponseDto(InventoryConstants.MESSAGE_201, InventoryConstants.STATUS_201));
   }

   @GetMapping
   public ResponseEntity<List<InventoryDto>> getAllInventoryRecords(){
      List<InventoryDto> inventoryRecords = inventoryService.fetchAll();
      return ResponseEntity.ok(inventoryRecords);
   }

   @GetMapping("/{productId}")
   public ResponseEntity<InventoryDto> getInventoryRecord(@PathVariable String productId){
      InventoryDto inventoryRecord = inventoryService.inventoryDetails(productId);
      return new ResponseEntity<>(inventoryRecord, HttpStatus.OK);
   }

   @PatchMapping("/{productId}")
   public ResponseEntity<ResponseDto> updateInventoryRecord(@PathVariable String productId, @RequestBody InventoryDto inventoryDto){
      inventoryService.updateInventory(productId, inventoryDto);
      return  ResponseEntity.ok(new ResponseDto(InventoryConstants.MESSAGE_200, InventoryConstants.STATUS_200));
   }
}
