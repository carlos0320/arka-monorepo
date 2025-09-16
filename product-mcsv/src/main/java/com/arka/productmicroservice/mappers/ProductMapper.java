package com.arka.productmicroservice.mappers;

import com.arka.productmicroservice.dtos.BrandDto;
import com.arka.productmicroservice.dtos.CategoryDto;
import com.arka.productmicroservice.dtos.ProductDto;
import com.arka.productmicroservice.entities.Brand;
import com.arka.productmicroservice.entities.Category;
import com.arka.productmicroservice.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductMapper {
   public static ProductDto toProductDto(Product product) {
      ProductDto productDto = new ProductDto();
      productDto.setProductId(product.getProductId());
      productDto.setName(product.getName());
      productDto.setPrice(product.getPrice());
      productDto.setDescription(product.getDescription());
      productDto.setImageUrl(product.getImageUrl());

      BrandDto brandDto = BrandMapper.toBrandDto(product.getBrand(), new BrandDto());
      CategoryDto categoryDto = CategoryMapper.toCategoryDto(product.getCategory(), new CategoryDto());

      productDto.setBrand(brandDto);
      productDto.setCategory(categoryDto);

      return productDto;
   }

   public static  Product toProduct(ProductDto productDto) {
      Product product = new Product();
      product.setName(productDto.getName());
      product.setPrice(productDto.getPrice());
      product.setDescription(productDto.getDescription());
      product.setImageUrl(productDto.getImageUrl());

      Brand brand = BrandMapper.toBrand(new Brand(), productDto.getBrand());
      Category category = CategoryMapper.toCategory(new Category(), productDto.getCategory());

      product.setBrand(brand);
      product.setCategory(category);

      return product;
   }
}
