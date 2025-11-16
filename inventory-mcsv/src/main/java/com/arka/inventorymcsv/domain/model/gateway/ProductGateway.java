package com.arka.inventorymcsv.domain.model.gateway;

import com.arka.inventorymcsv.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface ProductGateway {
  Mono<Product> createProduct(Product product);
  Mono<Product> findProductByName(String name);
  Mono<Product> findProductById(Long productId);
  Flux<Product> listAllProducts();
  Mono<Void> updateProduct(Product product);
  Flux<Product> getProductsByIds(List<Long> ids);
  Flux<Product> getLowStockProducts();
}
