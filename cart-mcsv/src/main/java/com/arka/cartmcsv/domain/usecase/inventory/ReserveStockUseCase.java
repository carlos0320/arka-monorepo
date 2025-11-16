package com.arka.cartmcsv.domain.usecase.inventory;

import com.arka.cartmcsv.domain.model.Product;
import com.arka.cartmcsv.domain.model.gateway.InventoryGateway;

public class ReserveStockUseCase {

  private final InventoryGateway inventoryGateway;

  public ReserveStockUseCase(InventoryGateway inventoryGateway) {
    this.inventoryGateway = inventoryGateway;
  }

  public Product execute(Long productId, Integer quantity){
    try{
      return inventoryGateway.reserveStock(productId, quantity);
    }catch (Exception ex){
      throw new RuntimeException("Unable to reserve stock for product " + productId, ex);
    }

  }

  private void ValidateParameters(Long productId, Integer quantity){
    if (productId == null || productId <= 0){
      throw new IllegalArgumentException("Invalid product id");
    }

    if (quantity == null || quantity <= 0){
      throw  new IllegalArgumentException("Invalid required quantity");
    }
  }
}
