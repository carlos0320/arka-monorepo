package com.arka.inventorymcsv.domain.usecase.category;

import com.arka.inventorymcsv.domain.model.Category;
import com.arka.inventorymcsv.domain.model.gateway.CategoryGateway;
import reactor.core.publisher.Flux;

public class GetAllCategoriesUseCase {
  private final CategoryGateway categoryGateway;


  public GetAllCategoriesUseCase(CategoryGateway categoryGateway) {
    this.categoryGateway = categoryGateway;
  }

  public Flux<Category> execute() {
    return categoryGateway.getAllCategories();
  }
}
