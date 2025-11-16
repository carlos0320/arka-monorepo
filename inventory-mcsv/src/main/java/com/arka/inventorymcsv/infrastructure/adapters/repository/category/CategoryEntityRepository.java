package com.arka.inventorymcsv.infrastructure.adapters.repository.category;

import com.arka.inventorymcsv.infrastructure.adapters.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CategoryEntityRepository extends ReactiveCrudRepository<CategoryEntity, Long> {
  Mono<CategoryEntity> findCategoryEntitiesByCategoryId(Long categoryId);
}
