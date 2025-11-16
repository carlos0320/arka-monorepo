package com.arka.inventorymcsv.domain.usecase.product;

import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.domain.model.gateway.ProductGateway;
import reactor.core.publisher.Flux;

public class ListLowStockProductsUseCase {
  private final ProductGateway productGateway;

  public ListLowStockProductsUseCase(ProductGateway productGateway) {
    this.productGateway = productGateway;
  }

  public Flux<Product> execute(){
    return productGateway.getLowStockProducts();
  }
}
