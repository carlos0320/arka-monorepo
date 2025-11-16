package com.arka.inventorymcsv.domain.usecase.product;

import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.domain.model.gateway.ProductGateway;
import reactor.core.publisher.Mono;

public class ConfirmStockUseCase {
  private final ProductGateway productGateway;

  public ConfirmStockUseCase(ProductGateway productGateway) {
    this.productGateway = productGateway;
  }

  public Mono<Product> execute(Long productId, Integer quantity){
    return productGateway.findProductById(productId)
            .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
            .flatMap(existingProduct ->{
              if (existingProduct.getReservedStock() < quantity) {
                return Mono.error(new RuntimeException("Requested quantity exceeds reserved stock"));
              }
              existingProduct.setStock(existingProduct.getStock() - quantity);
              existingProduct.setReservedStock(existingProduct.getReservedStock() - quantity);
              existingProduct.setAvailableStock(
                      existingProduct.getStock() - existingProduct.getReservedStock()
              );

              return productGateway.updateProduct(existingProduct)
                      .thenReturn(existingProduct);
            });
  }
}
