package com.arka.cartmcsv.domain.usecase.inventory;

import com.arka.cartmcsv.domain.model.Product;
import com.arka.cartmcsv.domain.model.gateway.InventoryGateway;

import java.util.List;

public class GetProductsByIdsUseCase {
  private final InventoryGateway inventoryGateway;

  public GetProductsByIdsUseCase(InventoryGateway inventoryGateway) {
    this.inventoryGateway = inventoryGateway;
  }

  public List<Product> execute(List<Long> productIds){
    try{
      return inventoryGateway.getProductsByIds(productIds);
    } catch (Exception e) {
      throw new RuntimeException("Unable to fetch products ", e);
    }
  }

}
