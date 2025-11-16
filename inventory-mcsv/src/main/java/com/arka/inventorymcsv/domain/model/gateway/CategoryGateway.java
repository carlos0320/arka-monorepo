package com.arka.inventorymcsv.domain.model.gateway;

import com.arka.inventorymcsv.domain.model.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface CategoryGateway {
  Mono<Category> createCategory(Category category);
  Flux<Category> getAllCategories();

  Mono<Category> findById(Long categoryId);

}
