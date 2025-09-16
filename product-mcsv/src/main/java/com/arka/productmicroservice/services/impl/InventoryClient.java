package com.arka.productmicroservice.services.impl;

import com.arka.productmicroservice.dtos.InventoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class InventoryClient {

   private final RestClient restClient;

   public void createInventoryForProduct(Long productId){
      System.out.println("PRODUCT1111111111***" + (productId));
      InventoryDto inventoryDto = new InventoryDto();
      inventoryDto.setProductId(productId);
      restClient.post()
              .uri("/api/inventory")
              .body(inventoryDto)       // JSON body { "productId": 123 }
              .retrieve()
              .toBodilessEntity(); // handles 201/204 with no body
   }
}
