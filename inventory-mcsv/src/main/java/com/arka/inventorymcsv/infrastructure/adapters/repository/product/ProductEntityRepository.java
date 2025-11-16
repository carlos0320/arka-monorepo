package com.arka.inventorymcsv.infrastructure.adapters.repository.product;

import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.infrastructure.adapters.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductEntityRepository extends ReactiveCrudRepository<ProductEntity, Long> {
  Mono<ProductEntity> findProductByName(String name);
  Flux<ProductEntity> findProductEntitiesByIsActiveTrue();
  Mono<ProductEntity> findProductByProductId(Long productId);
  Flux<ProductEntity> findProductEntitiesByProductIdIsIn(List<Long> productIds);

  @Query("SELECT * FROM products.product as p where p.stock < p.min_stock")
  Flux<ProductEntity> findLowStockProducts();
}
