package com.arka.inventorymcsv.domain.usecase.product;

import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.domain.model.gateway.ProductGateway;
import reactor.core.publisher.Mono;

public class RestockUseCase {
  private final ProductGateway productGateway;

  public RestockUseCase(ProductGateway productGateway) {
    this.productGateway = productGateway;
  }

  public Mono<Product> execute(Long productId, Integer quantity){
    if (quantity == null || quantity <= 0) {
      return Mono.error(new IllegalArgumentException("Quantity must be greater than 0"));
    }
    return productGateway.findProductById(productId)
            .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
            .flatMap(existingProduct ->{
              existingProduct.setStock(existingProduct.getStock() + quantity);
              existingProduct.setAvailableStock(existingProduct.getAvailableStock() + quantity);

              return productGateway.updateProduct(existingProduct)
                      .thenReturn(existingProduct);
            });
  }
}
