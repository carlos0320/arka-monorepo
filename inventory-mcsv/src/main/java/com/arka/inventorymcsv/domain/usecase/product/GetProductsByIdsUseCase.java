package com.arka.inventorymcsv.domain.usecase.product;

import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.domain.model.gateway.ProductGateway;
import reactor.core.publisher.Flux;

import java.util.List;

public class GetProductsByIdsUseCase {
  private final ProductGateway productGateway;


  public GetProductsByIdsUseCase(ProductGateway productGateway) {
    this.productGateway = productGateway;
  }

  public Flux<Product> execute(List<Long> productIds){
    return productGateway.getProductsByIds(productIds);
  }
}
