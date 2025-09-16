package com.arka.productmicroservice.services;

import com.arka.productmicroservice.dtos.ProductDto;
import com.arka.productmicroservice.entities.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
   ProductDto createProduct(ProductDto productDto);
   List<ProductDto> fetchAllProducts();
   ProductDto fetchProduct(String id);
   List<ProductDto> fetchAllProductsByCategory(String categoryName);
   void updateProduct(String id, ProductDto productDto);
   void deleteProduct(String id);
}
