package com.arka.inventorymcsv.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  private Long productId;
  private String name;
  private String description;
  private String imageUrl;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Integer stock;
  private Integer minStock;
  private Integer reservedStock = 0;
  private Integer availableStock;
  private Boolean isActive = true;
  private BigDecimal price;

  private Category category;
  private Brand brand;

}
