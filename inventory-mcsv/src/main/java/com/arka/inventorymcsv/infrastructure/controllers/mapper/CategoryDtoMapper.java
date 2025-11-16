package com.arka.inventorymcsv.infrastructure.controllers.mapper;

import com.arka.inventorymcsv.domain.model.Category;
import com.arka.inventorymcsv.infrastructure.adapters.entity.CategoryEntity;
import com.arka.inventorymcsv.infrastructure.controllers.dto.CategoryDto;

public class CategoryDtoMapper {

  public static CategoryDto toDto(Category category) {
    CategoryDto categoryDto = new CategoryDto();
    categoryDto.setCategoryId(category.getCategoryId());
    categoryDto.setName(category.getName());
    return categoryDto;
  }

  public static Category toDomain(CategoryDto categoryDto) {
    Category category = new Category();
    category.setCategoryId(categoryDto.getCategoryId());
    category.setName(categoryDto.getName());
    return category;
  }
}
