package com.arka.productmicroservice.controller;

import com.arka.productmicroservice.constants.ProductConstants;
import com.arka.productmicroservice.dtos.ProductDto;
import com.arka.productmicroservice.dtos.ResponseDto;
import com.arka.productmicroservice.entities.Product;
import com.arka.productmicroservice.services.IProductService;
import com.arka.productmicroservice.services.impl.InventoryClient;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
   private IProductService productService;

   private InventoryClient inventoryClient;

   @PostMapping
   public ResponseEntity<ResponseDto> createProduct(@RequestBody ProductDto productDto) {
      ProductDto productCreated = productService.createProduct(productDto);
      inventoryClient.createInventoryForProduct(productCreated.getProductId());
      return new ResponseEntity<>(new ResponseDto(ProductConstants.MESSAGE_201, ProductConstants.STATUS_201), HttpStatus.OK);
   }

   @GetMapping
   public ResponseEntity<List<ProductDto>> fetchAllProducts() {
      List<ProductDto> allProducts = productService.fetchAllProducts();
      return ResponseEntity.ok(allProducts);
   }

   @GetMapping("/{id}")
   public ResponseEntity<ProductDto> fetchProductById(@PathVariable String id) {
      ProductDto product = productService.fetchProduct(id);
      return ResponseEntity.ok(product);
   }

   @GetMapping("/by-category")
   public ResponseEntity<List<ProductDto>> fetchAllProductsByCategory(@RequestParam String categoryName) {
      List<ProductDto> productsByCategory = productService.fetchAllProductsByCategory(categoryName);
      return ResponseEntity.ok(productsByCategory);
   }

   @PutMapping("/{id}")
   public ResponseEntity<ResponseDto> updateProduct(@PathVariable String id, @RequestBody ProductDto productDto) {
      productService.updateProduct(id, productDto);
      return ResponseEntity.ok(new ResponseDto(ProductConstants.MESSAGE_200, ProductConstants.STATUS_200));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<ResponseDto> deleteProduct(@PathVariable String id) {
      productService.deleteProduct(id);
      return ResponseEntity.ok(new ResponseDto(ProductConstants.MESSAGE_200, ProductConstants.STATUS_200));
   }


}
