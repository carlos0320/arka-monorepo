package com.arka.cartmcsv.domain.usecase.inventory;

import com.arka.cartmcsv.domain.model.Product;
import com.arka.cartmcsv.domain.model.gateway.InventoryGateway;

public class ReleaseStockUseCase {

  private final InventoryGateway inventoryGateway;

  public ReleaseStockUseCase(InventoryGateway inventoryGateway) {
    this.inventoryGateway = inventoryGateway;
  }

  public Product execute(Long productId, Integer quantity){
    try{
      return inventoryGateway.releaseStock(productId, quantity);
    }catch (Exception ex){
      throw new RuntimeException("Unable to reserve stock for product " + productId, ex);
    }
  }
}
