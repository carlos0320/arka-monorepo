package com.arka.productmicroservice.mappers;

import com.arka.productmicroservice.dtos.CategoryDto;
import com.arka.productmicroservice.entities.Category;

public class CategoryMapper {

   public static CategoryDto toCategoryDto(Category category, CategoryDto categoryDto){
      categoryDto.setName(category.getName());
      return categoryDto;
   }

   public static  Category toCategory(Category category,CategoryDto categoryDto ){
      category.setName(categoryDto.getName());
      return category;
   }
}
