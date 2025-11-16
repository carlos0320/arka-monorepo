package com.arka.inventorymcsv.infrastructure.adapters.mappers;

import com.arka.inventorymcsv.domain.model.Category;
import com.arka.inventorymcsv.infrastructure.adapters.entity.CategoryEntity;
import lombok.Data;

@Data
public class CategoryMapper {
  public static CategoryEntity toEntity(Category category) {
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setCategoryId(category.getCategoryId());
    categoryEntity.setName(category.getName());
    return categoryEntity;
  }

  public static Category toDomain(CategoryEntity categoryEntity) {
    Category category = new Category();
    category.setCategoryId(categoryEntity.getCategoryId());
    category.setName(categoryEntity.getName());
    return category;
  }
}
