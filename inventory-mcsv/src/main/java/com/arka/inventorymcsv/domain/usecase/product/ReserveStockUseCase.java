package com.arka.inventorymcsv.domain.usecase.product;

import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.domain.model.gateway.ProductGateway;
import reactor.core.publisher.Mono;

public class ReserveStockUseCase {
  private final ProductGateway productGateway;

  public ReserveStockUseCase(ProductGateway productGateway) {
    this.productGateway = productGateway;
  }

  public Mono<Product> execute(Long productId, Integer quantity){
    return productGateway.findProductById(productId)
            .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
            .flatMap(existingProduct ->{
              if (existingProduct.getAvailableStock() < quantity) {
                return Mono.error(new RuntimeException("Requested quantity exceeds available stock"));
              }
              existingProduct.setAvailableStock(existingProduct.getAvailableStock() - quantity);
              existingProduct.setReservedStock(existingProduct.getReservedStock() + quantity);

              return productGateway.updateProduct(existingProduct)
                      .thenReturn(existingProduct);
            });
  }
}
