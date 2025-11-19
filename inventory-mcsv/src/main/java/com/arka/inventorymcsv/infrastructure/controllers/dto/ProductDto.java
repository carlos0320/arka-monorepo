package com.arka.inventorymcsv.infrastructure.controllers.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductDto {
  private Long productId;

  private String name;
  private String description;
  private String imageUrl;
  private Integer stock;
  private BigDecimal price;
  private Integer minStock;
  private Integer availableStock;
  private BrandDto brand;
  private CategoryDto category;

}
