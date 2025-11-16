package com.arka.inventorymcsv.infrastructure.adapters.entity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema="products", name="product")
public class ProductEntity {

  @Id
  private Long productId;

  @NotBlank(message = "Product name is required")
  private String name;

  @NotBlank(message = "description is required")
  private String description;

  private String imageUrl;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @NotNull(message = "stock is required")
  @Min(value = 1, message = "Stock must be a positive integer and greater than 0")
  private Integer stock;

  @NotNull(message = "Min stock is required")
  @Min(value= 1, message = "Stock must be a positive integer and greater than 0")
  private Integer minStock;

  private Integer reservedStock = 0;
  private Integer availableStock;
  private Boolean isActive = true;

  @NotNull(message = "Price is required")
  @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
  private BigDecimal price;

  private Long brandId;
  private Long categoryId;

}
