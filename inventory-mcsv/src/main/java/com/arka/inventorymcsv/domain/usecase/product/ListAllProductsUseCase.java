package com.arka.inventorymcsv.domain.usecase.product;

import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.domain.model.gateway.BrandGateway;
import com.arka.inventorymcsv.domain.model.gateway.CategoryGateway;
import com.arka.inventorymcsv.domain.model.gateway.ProductGateway;
import reactor.core.publisher.Flux;

public class ListAllProductsUseCase {
  private final ProductGateway productGateway;
  public ListAllProductsUseCase(ProductGateway productGateway) {
    this.productGateway = productGateway;
  }

  public Flux<Product> execute(){
    return productGateway.listAllProducts();
  }
}
