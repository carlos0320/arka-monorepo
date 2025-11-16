package com.arka.inventorymcsv.domain.usecase.product;

import com.arka.inventorymcsv.domain.model.Brand;
import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.domain.model.gateway.BrandGateway;
import com.arka.inventorymcsv.domain.model.gateway.CategoryGateway;
import com.arka.inventorymcsv.domain.model.gateway.ProductGateway;
import reactor.core.publisher.Mono;

public class GetProductDetailUseCase {
  private final ProductGateway productGateway;

  public GetProductDetailUseCase(ProductGateway productGateway) {
    this.productGateway = productGateway;
  }

  public Mono<Product> execute(Long productId){
    return productGateway.findProductById(productId)
            .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
            .flatMap(existingProduct -> productGateway.findProductById(productId));
  }
}
