package com.arka.cartmcsv.domain.usecase.inventory;

import com.arka.cartmcsv.domain.exceptions.InvalidProductIdException;
import com.arka.cartmcsv.domain.exceptions.InvalidQuantityException;
import com.arka.cartmcsv.domain.exceptions.UnableToReserveStockException;
import com.arka.cartmcsv.domain.model.Product;
import com.arka.cartmcsv.domain.model.gateway.InventoryGateway;

public class ReserveStockUseCase {

  private final InventoryGateway inventoryGateway;

  public ReserveStockUseCase(InventoryGateway inventoryGateway) {
    this.inventoryGateway = inventoryGateway;
  }

  public Product execute(Long productId, Integer quantity){
    ValidateParameters(productId, quantity);
    try{
      return inventoryGateway.reserveStock(productId, quantity);
    }catch (Exception ex){
      throw new UnableToReserveStockException("Unable to reserve stock for product " + productId);
    }

  }

  private void ValidateParameters(Long productId, Integer quantity){
    if (productId == null || productId <= 0) {
      throw new InvalidProductIdException();
    }

    if (quantity == null || quantity <= 0){
      throw new InvalidQuantityException();
    }
  }
}
