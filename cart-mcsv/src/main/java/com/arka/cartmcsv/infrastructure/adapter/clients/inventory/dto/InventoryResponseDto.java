package com.arka.cartmcsv.infrastructure.adapter.clients.inventory.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryResponseDto {
  private DataDto data;

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class DataDto{
    private ProductDto product;
    private List<ProductDto> products;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ProductDto {
    private Long productId;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private Integer availableStock;
    private Integer minStock;
    private BrandDto brand;
    private CategoryDto category;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class BrandDto {
    private String name;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CategoryDto {
    private String name;
  }
}
