package com.arka.inventorymcsv.domain.usecase.product;

import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.domain.model.gateway.BrandGateway;
import com.arka.inventorymcsv.domain.model.gateway.CategoryGateway;
import com.arka.inventorymcsv.domain.model.gateway.ProductGateway;
import reactor.core.publisher.Mono;

public class UpdateProductUseCase {
  private final ProductGateway productGateway;
  private final BrandGateway brandGateway;
  private final CategoryGateway categoryGateway;

  public UpdateProductUseCase(ProductGateway productGateway, BrandGateway brandGateway, CategoryGateway categoryGateway) {
    this.productGateway = productGateway;
    this.brandGateway = brandGateway;
    this.categoryGateway = categoryGateway;
  }

  public Mono<Void> execute(Product product) {
    return productGateway.findProductById(product.getProductId())
            .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
            .flatMap(existingProduct ->
                    productGateway.updateProduct(product)
            )
            .then();
  }

}
