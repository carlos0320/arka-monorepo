package com.arka.inventorymcsv.infrastructure.adapters.mappers;

import com.arka.inventorymcsv.domain.model.Brand;
import com.arka.inventorymcsv.domain.model.Category;
import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.infrastructure.adapters.entity.ProductEntity;

public class ProductMapper {

  public static ProductEntity toEntity(Product product) {
    ProductEntity entity = new ProductEntity();

    entity.setProductId(product.getProductId());
    entity.setName(product.getName());
    entity.setIsActive(product.getIsActive());
    entity.setPrice(product.getPrice());
    entity.setDescription(product.getDescription());
    entity.setCreatedAt(product.getCreatedAt());
    entity.setUpdatedAt(product.getUpdatedAt());
    entity.setImageUrl(product.getImageUrl());
    entity.setMinStock(product.getMinStock());
    entity.setReservedStock(product.getReservedStock());
    entity.setStock(product.getStock());
    if (product.getAvailableStock() == null){
      Integer availableStock = product.getStock() - product.getReservedStock();
      entity.setAvailableStock(availableStock);
    }else{
      entity.setAvailableStock(product.getAvailableStock());
    }

    if (product.getBrand() != null && product.getBrand().getBrandId() != null) {
      entity.setBrandId(product.getBrand().getBrandId());
    }

    if (product.getCategory() != null && product.getCategory().getCategoryId() != null) {
      entity.setCategoryId(product.getCategory().getCategoryId());
    }

    return entity;
  }

  public static Product toDomain(ProductEntity entity, Category category, Brand brand) {
    Product product = new Product();
    product.setProductId(entity.getProductId());
    product.setName(entity.getName());
    product.setIsActive(entity.getIsActive());
    product.setPrice(entity.getPrice());
    product.setDescription(entity.getDescription());
    product.setCreatedAt(entity.getCreatedAt());
    product.setUpdatedAt(entity.getUpdatedAt());
    product.setImageUrl(entity.getImageUrl());
    product.setMinStock(entity.getMinStock());
    product.setStock(entity.getStock());
    product.setAvailableStock(entity.getAvailableStock());
    product.setReservedStock(entity.getReservedStock());
    product.setBrand(brand);
    product.setCategory(category);
    return product;
  }
}
