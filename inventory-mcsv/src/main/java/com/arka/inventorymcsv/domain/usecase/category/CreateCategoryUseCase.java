package com.arka.inventorymcsv.domain.usecase.category;

import com.arka.inventorymcsv.domain.exceptions.ValidationException;
import com.arka.inventorymcsv.domain.model.Category;
import com.arka.inventorymcsv.domain.model.gateway.CategoryGateway;
import reactor.core.publisher.Mono;

public class CreateCategoryUseCase {
  private final CategoryGateway categoryGateway;

  public CreateCategoryUseCase(CategoryGateway categoryGateway) {
    this.categoryGateway = categoryGateway;
  }

  public Mono<Category> execute(Category category){

    if (category.getName() == null || category.getName().isEmpty()) {
      return Mono.error(new ValidationException("Category name is required"));
    }

    return categoryGateway.createCategory(category);
  }
}
