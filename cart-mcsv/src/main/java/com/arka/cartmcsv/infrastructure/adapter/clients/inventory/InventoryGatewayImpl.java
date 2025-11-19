package com.arka.cartmcsv.infrastructure.adapter.clients.inventory;

import com.arka.cartmcsv.domain.exceptions.InventoryServiceException;
import com.arka.cartmcsv.domain.exceptions.InventoryServiceUnavailableException;
import com.arka.cartmcsv.domain.model.Product;
import com.arka.cartmcsv.domain.model.gateway.InventoryGateway;
import com.arka.cartmcsv.infrastructure.adapter.clients.inventory.dto.BatchProductRequest;
import com.arka.cartmcsv.infrastructure.adapter.clients.inventory.dto.InventoryRequestDto;
import com.arka.cartmcsv.infrastructure.adapter.clients.inventory.dto.InventoryResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryGatewayImpl implements InventoryGateway {

  private final InventoryHttpClient inventoryHttpClient;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public InventoryGatewayImpl(InventoryHttpClient inventoryHttpClient) {
    this.inventoryHttpClient = inventoryHttpClient;
  }

  @Override
  @Retryable(
          retryFor = { ResourceAccessException.class, HttpServerErrorException.class },
          maxAttempts = 3,
          backoff = @Backoff(delay = 500, multiplier = 2)
  )
  @CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackReserveStock")
  public Product reserveStock(Long productId, Integer quantity) {
    try{
      InventoryRequestDto inventoryRequestDto = new InventoryRequestDto();

      inventoryRequestDto.setProductId(productId);
      inventoryRequestDto.setQuantity(quantity);

      InventoryResponseDto inventoryResponseDto = inventoryHttpClient.reserveStock(inventoryRequestDto);

      validateResponse(inventoryResponseDto);
      return getProduct(inventoryResponseDto);

    }catch (HttpClientErrorException.BadRequest e) {
      throw new InventoryServiceException("Bad request to Inventory Service: " + e.getMessage());
    }
  }

  public Product fallbackReserveStock(Long productId, Integer quantity, Throwable t) {
    throw new InventoryServiceUnavailableException("Inventory Service unavailable. Circuit breaker triggered.");
  }

  @Override
  @Retryable(
          retryFor = { ResourceAccessException.class, HttpServerErrorException.class },
          maxAttempts = 3,
          backoff = @Backoff(delay = 500, multiplier = 2)
  )
  @CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackReleaseStock")
  public Product releaseStock(Long productId, Integer quantity) {
    try{
      InventoryRequestDto inventoryRequestDto = new InventoryRequestDto();

      inventoryRequestDto.setProductId(productId);
      inventoryRequestDto.setQuantity(quantity);

      InventoryResponseDto inventoryResponseDto = inventoryHttpClient.releaseStock(inventoryRequestDto);

      validateResponse(inventoryResponseDto);

      return getProduct(inventoryResponseDto);
    }catch (HttpClientErrorException.BadRequest e) {
      throw new InventoryServiceException("Bad request to Inventory Service: " + e.getMessage());
    }
  }
  public Product fallbackReleaseStock(Long productId, Integer quantity, Throwable t) {
    throw new InventoryServiceUnavailableException("Inventory Service unavailable. Circuit breaker triggered.");
  }

  @Override
  @Retryable(
          retryFor = { ResourceAccessException.class, HttpServerErrorException.class },
          maxAttempts = 3,
          backoff = @Backoff(delay = 500, multiplier = 2)
  )
  @CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackGetProducts")
  public List<Product> getProductsByIds(List<Long> productIds) {
    try{
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

    }catch (HttpClientErrorException.BadRequest e) {
      throw new InventoryServiceException("Bad request to Inventory Service: " + e.getMessage());
    }
  }

  public List<Product> fallbackGetProducts(List<Long> productIds, Throwable t) {
    throw new InventoryServiceUnavailableException("Inventory Service unavailable when fetching products.");
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
