package com.arka.productmicroservice.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
   private Long productId;

   private String name;

   private String imageUrl;

   private String description;

   private BigDecimal price;

   private BrandDto brand;

   private CategoryDto category;
}
