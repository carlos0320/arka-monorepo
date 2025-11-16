package com.arka.cartmcsv.domain.model.gateway;

import com.arka.cartmcsv.domain.model.Product;

import java.util.List;

public interface InventoryGateway {
  Product reserveStock(Long productId, Integer quantity);
  Product releaseStock(Long productId, Integer quantity);
  List<Product> getProductsByIds(List<Long> productIds);
}
