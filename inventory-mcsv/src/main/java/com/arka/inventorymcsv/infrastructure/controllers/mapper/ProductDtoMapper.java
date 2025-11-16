package com.arka.inventorymcsv.infrastructure.controllers.mapper;

import com.arka.inventorymcsv.domain.model.Brand;
import com.arka.inventorymcsv.domain.model.Category;
import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.infrastructure.adapters.mappers.BrandMapper;
import com.arka.inventorymcsv.infrastructure.controllers.dto.BrandDto;
import com.arka.inventorymcsv.infrastructure.controllers.dto.CategoryDto;
import com.arka.inventorymcsv.infrastructure.controllers.dto.ProductDto;

public class ProductDtoMapper {

  public static ProductDto toDto(Product product, Brand brand, Category category) {
    ProductDto productDto = new ProductDto();
    productDto.setProductId(product.getProductId());
    productDto.setName(product.getName());
    productDto.setDescription(product.getDescription());
    productDto.setPrice(product.getPrice());

    productDto.setImageUrl(product.getImageUrl());
    productDto.setStock(product.getStock());
    productDto.setAvailableStock(product.getAvailableStock());
    productDto.setMinStock(product.getMinStock());

    if (brand != null) {
      BrandDto brandDto = new BrandDto();
      brandDto.setBrandId(brand.getBrandId());
      brandDto.setName(brand.getName());
      brandDto.setLogo(brand.getLogo());
      productDto.setBrand(brandDto);
    }

    if (category != null) {
      CategoryDto categoryDto = new CategoryDto();
      categoryDto.setCategoryId(category.getCategoryId());
      categoryDto.setName(category.getName());
      productDto.setCategory(categoryDto);
    }

    return productDto;
  }

  public static Product toDomain(ProductDto productDto) {
    Product product = new Product();

    product.setProductId(productDto.getProductId());
    product.setName(productDto.getName());
    product.setDescription(productDto.getDescription());
    product.setPrice(productDto.getPrice());
    product.setImageUrl(productDto.getImageUrl());
    product.setStock(productDto.getStock());
    product.setAvailableStock(productDto.getAvailableStock());
    product.setMinStock(productDto.getMinStock());

    if (productDto.getCategory() != null) {
      Category category = CategoryDtoMapper.toDomain(productDto.getCategory());
      product.setCategory(category);
    }

    if (productDto.getBrand() != null) {
      Brand brand = BrandDtoMapper.toDomain(productDto.getBrand());
      product.setBrand(brand);
    }

    return product;
  }
}
