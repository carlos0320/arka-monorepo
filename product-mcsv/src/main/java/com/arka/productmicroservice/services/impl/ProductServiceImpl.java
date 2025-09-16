package com.arka.productmicroservice.services.impl;

import com.arka.productmicroservice.dtos.BrandDto;
import com.arka.productmicroservice.dtos.CategoryDto;
import com.arka.productmicroservice.dtos.ProductDto;
import com.arka.productmicroservice.entities.Brand;
import com.arka.productmicroservice.entities.Category;
import com.arka.productmicroservice.entities.Product;
import com.arka.productmicroservice.exception.ResourceAlreadyExistsException;
import com.arka.productmicroservice.exception.ResourceNotFoundException;
import com.arka.productmicroservice.mappers.BrandMapper;
import com.arka.productmicroservice.mappers.CategoryMapper;
import com.arka.productmicroservice.mappers.ProductMapper;
import com.arka.productmicroservice.repositories.BrandRepository;
import com.arka.productmicroservice.repositories.CategoryRepository;
import com.arka.productmicroservice.repositories.ProductRepository;
import com.arka.productmicroservice.services.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements IProductService {

   private ProductRepository productRepository;
   private CategoryRepository categoryRepository;
   private BrandRepository brandRepository;

   @Override
   public ProductDto createProduct(ProductDto productdto) {

      Product product = ProductMapper.toProduct(productdto);
      Optional<Product> optionalProduct = productRepository.findByName(product.getName());

      if (optionalProduct.isPresent()) {
         throw new ResourceAlreadyExistsException("Product already exists with this name: " + productdto.getName() );
      }

      // handle category
      Category category = categoryRepository.findByName(productdto.getCategory().getName())
              .orElseGet(() -> {
                 // create a new one if it does not exist
                 Category newCategory = new Category();
                 newCategory.setName(productdto.getCategory().getName());
                 return categoryRepository.save(newCategory);
              });

      // handle brand
      Brand brand = brandRepository.findByName(productdto.getBrand().getName())
              .orElseGet(() -> {
                 // create a new one if it does not exist
                 Brand newBrand = new Brand();
                 newBrand.setName(productdto.getBrand().getName());
                 newBrand.setLogo(productdto.getBrand().getLogo());
                 return brandRepository.save(newBrand);
              });

      // assign managed entities
      product.setCategory(category);
      product.setBrand(brand);

      // save product
      productRepository.save(product);
      return ProductMapper.toProductDto(product);
   }

   @Override
   public List<ProductDto> fetchAllProducts() {
      List<ProductDto> allProducts = productRepository.findAll()
              .stream()
              .filter(product -> product.isActive())
              .map(ProductMapper::toProductDto)
              .toList();

      return allProducts;
   }

   @Override
   public ProductDto fetchProduct(String id) {
      Optional<Product> product = productRepository.findById(Long.valueOf(id));

      if (product.isEmpty() || !product.get().isActive()) {
         throw new ResourceNotFoundException("Product not found with this id: " + id);
      }

      ProductDto productDto = product.map(ProductMapper::toProductDto).get();
      return productDto;
   }

   @Override
   public List<ProductDto> fetchAllProductsByCategory(String categoryName) {
      List<ProductDto> productsByCategory = productRepository.findByCategoryName(categoryName)
              .stream()
              .filter(product -> product.isActive())
              .map(ProductMapper::toProductDto)
              .toList();
      return productsByCategory;
   }

   @Override
   public void updateProduct(String id, ProductDto productDto) {
      Product product = productRepository.findById(Long.valueOf(id))
              .orElseThrow(() -> {
                 throw new ResourceNotFoundException("Product not found with this id: " + id);
              });

      if (!product.isActive()){
         throw new ResourceNotFoundException("Product not found with this id: " + id);
      }

      product.setName(productDto.getName());
      product.setDescription(productDto.getDescription());
      product.setPrice(productDto.getPrice());
      product.setImageUrl(productDto.getImageUrl());

      // handle category
      if (productDto.getCategory() != null){
         Category category = categoryRepository.findByName(productDto.getCategory().getName())
                 .orElseGet(() ->{
                    Category newCategory = new Category();
                    newCategory.setName(productDto.getCategory().getName());
                    return categoryRepository.save(newCategory);
                 });
         product.setCategory(category);
      }

      if (productDto.getBrand() != null){
         Brand brand = brandRepository.findByName(productDto.getBrand().getName())
                 .orElseGet(() ->{
                    Brand newBrand = new Brand();
                    newBrand.setName(productDto.getBrand().getName());
                    newBrand.setLogo(productDto.getBrand().getLogo());
                    return brandRepository.save(newBrand);
                 });
         product.setBrand(brand);
      }

      productRepository.save(product);
   }

   @Override
   public void deleteProduct(String id){
      Product product = productRepository.findById(Long.valueOf(id))
              .orElseThrow(() -> {
                 throw new ResourceNotFoundException("Product not found with this id: " + id);
              });
      product.setActive(false);
      productRepository.save(product);
   }
}
