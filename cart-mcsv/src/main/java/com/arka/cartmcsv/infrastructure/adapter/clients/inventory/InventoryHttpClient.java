package com.arka.cartmcsv.infrastructure.adapter.clients.inventory;

import com.arka.cartmcsv.domain.model.Product;
import com.arka.cartmcsv.infrastructure.adapter.clients.inventory.dto.BatchProductRequest;
import com.arka.cartmcsv.infrastructure.adapter.clients.inventory.dto.InventoryRequestDto;
import com.arka.cartmcsv.infrastructure.adapter.clients.inventory.dto.InventoryResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange("/api/inventory/products")
public interface InventoryHttpClient {
  @PostExchange("/reserve-stock")
  InventoryResponseDto reserveStock(@RequestBody InventoryRequestDto inventoryRequestDto);

  @PostExchange("/release-stock")
  InventoryResponseDto releaseStock(@RequestBody InventoryRequestDto inventoryRequestDto);

  @PostExchange("/batch")
  InventoryResponseDto batchProducts(@RequestBody BatchProductRequest batchProductRequest);
}
