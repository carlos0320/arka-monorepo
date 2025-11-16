package com.arka.cartmcsv.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  private Long productId;
  private String name;
  private String brandName;
  private String categoryName;
  private String description;
  private String imageUrl;
  private Integer availableStock;
  private Integer minStock;
  private BigDecimal price;
}
