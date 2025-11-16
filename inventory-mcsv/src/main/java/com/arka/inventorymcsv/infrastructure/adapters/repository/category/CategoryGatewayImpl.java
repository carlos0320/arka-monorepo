package com.arka.inventorymcsv.infrastructure.adapters.repository.category;

import com.arka.inventorymcsv.domain.model.Category;
import com.arka.inventorymcsv.domain.model.gateway.CategoryGateway;
import com.arka.inventorymcsv.infrastructure.adapters.entity.CategoryEntity;
import com.arka.inventorymcsv.infrastructure.adapters.mappers.CategoryMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CategoryGatewayImpl implements CategoryGateway {
  private final CategoryEntityRepository categoryEntityRepository;

  public CategoryGatewayImpl(CategoryEntityRepository categoryEntityRepository) {
    this.categoryEntityRepository = categoryEntityRepository;
  }

  @Override
  public Mono<Category> createCategory(Category category) {
    CategoryEntity categoryEntity = CategoryMapper.toEntity(category);
    return categoryEntityRepository.save(categoryEntity)
            .map(CategoryMapper::toDomain);
  }

  @Override
  public Flux<Category> getAllCategories() {
    return categoryEntityRepository.findAll()
            .map(CategoryMapper::toDomain);
  }

  @Override
  public Mono<Category> findById(Long categoryId) {
    return categoryEntityRepository.findCategoryEntitiesByCategoryId(categoryId)
            .map(CategoryMapper::toDomain);
  }

}
