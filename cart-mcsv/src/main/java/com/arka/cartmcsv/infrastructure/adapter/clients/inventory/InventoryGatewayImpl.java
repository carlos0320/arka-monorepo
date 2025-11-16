package com.arka.cartmcsv.infrastructure.adapter.clients.inventory;

import com.arka.cartmcsv.domain.model.Product;
import com.arka.cartmcsv.domain.model.gateway.InventoryGateway;
import com.arka.cartmcsv.infrastructure.adapter.clients.inventory.dto.BatchProductRequest;
import com.arka.cartmcsv.infrastructure.adapter.clients.inventory.dto.InventoryRequestDto;
import com.arka.cartmcsv.infrastructure.adapter.clients.inventory.dto.InventoryResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryGatewayImpl implements InventoryGateway {

  private final InventoryHttpClient inventoryHttpClient;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public InventoryGatewayImpl(InventoryHttpClient inventoryHttpClient) {
    this.inventoryHttpClient = inventoryHttpClient;
  }

  @Override
  public Product reserveStock(Long productId, Integer quantity) {
    try{
      InventoryRequestDto inventoryRequestDto = new InventoryRequestDto();

      inventoryRequestDto.setProductId(productId);
      inventoryRequestDto.setQuantity(quantity);

      InventoryResponseDto inventoryResponseDto = inventoryHttpClient.reserveStock(inventoryRequestDto);

      validateResponse(inventoryResponseDto);
      return getProduct(inventoryResponseDto);

    }catch (Exception ex){
      throw new RuntimeException("Failed to communicate with Inventory Service", ex);
    }
  }

  @Override
  public Product releaseStock(Long productId, Integer quantity) {
    InventoryRequestDto inventoryRequestDto = new InventoryRequestDto();

    inventoryRequestDto.setProductId(productId);
    inventoryRequestDto.setQuantity(quantity);

    InventoryResponseDto inventoryResponseDto = inventoryHttpClient.releaseStock(inventoryRequestDto);

    validateResponse(inventoryResponseDto);

    return getProduct(inventoryResponseDto);
  }

  @Override
  public List<Product> getProductsByIds(List<Long> productIds) {

    BatchProductRequest batchProductRequest = new BatchProductRequest();
    batchProductRequest.setProductIds(productIds);

    InventoryResponseDto inventoryResponseDto = inventoryHttpClient.batchProducts(batchProductRequest);
    return inventoryResponseDto.getData().getProducts()
            .stream()
            .map(productDto -> {
              return new Product(
                      productDto.getProductId(),
                      productDto.getName(),
                      productDto.getBrand().getName(),
                      productDto.getCategory().getName(),
                      productDto.getDescription(),
                      productDto.getCategory().getName(),
                      productDto.getAvailableStock(),
                      productDto.getMinStock(),
                      productDto.getPrice()
              );
            })
            .collect(Collectors.toList());
  }

  private void validateResponse(InventoryResponseDto inventoryResponseDto){
    if (inventoryResponseDto == null || inventoryResponseDto.getData() == null) {
      throw new IllegalArgumentException("Empty response from Inventory Service");
    }

    InventoryResponseDto.ProductDto productDto = inventoryResponseDto.getData().getProduct();

    if (productDto == null) {
      throw new IllegalArgumentException("Product not found in response");
    }
  }

  private Product getProduct(InventoryResponseDto inventoryResponseDto){
    InventoryResponseDto.ProductDto productDto = inventoryResponseDto.getData().getProduct();
    return new Product(
            productDto.getProductId(),
            productDto.getName(),
            productDto.getBrand().getName(),
            productDto.getCategory().getName(),
            productDto.getDescription(),
            productDto.getCategory().getName(),
            productDto.getAvailableStock(),
            productDto.getMinStock(),
            productDto.getPrice()
    );
  }
}
