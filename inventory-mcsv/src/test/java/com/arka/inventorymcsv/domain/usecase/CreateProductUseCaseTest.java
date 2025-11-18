package com.arka.inventorymcsv.domain.usecase;


import com.arka.inventorymcsv.domain.model.Brand;
import com.arka.inventorymcsv.domain.model.Category;
import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.domain.model.gateway.BrandGateway;
import com.arka.inventorymcsv.domain.model.gateway.CategoryGateway;
import com.arka.inventorymcsv.domain.model.gateway.ProductGateway;
import com.arka.inventorymcsv.domain.exceptions.*;
import com.arka.inventorymcsv.domain.usecase.product.CreateProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateProductUseCaseTest {
  @Mock
  ProductGateway productGateway;

  @Mock
  BrandGateway brandGateway;

  @Mock
  CategoryGateway categoryGateway;

  CreateProductUseCase useCase;

  Product baseProduct;

  @BeforeEach
  void setUp() {
    useCase = new CreateProductUseCase(productGateway, brandGateway, categoryGateway);

    Brand brand = new Brand();
    brand.setBrandId(1L);
    brand.setName("MarcaX");

    Category category = new Category();
    category.setCategoryId(2L);
    category.setName("CatY");

    baseProduct = new Product();
    baseProduct.setProductId(null);
    baseProduct.setName("Producto A");
    baseProduct.setDescription("Descripción");
    baseProduct.setImageUrl("http://img");
    baseProduct.setCreatedAt(LocalDateTime.now());
    baseProduct.setUpdatedAt(LocalDateTime.now());
    baseProduct.setStock(10);
    baseProduct.setMinStock(2);
    baseProduct.setReservedStock(0);
    baseProduct.setAvailableStock(10);
    baseProduct.setIsActive(true);
    baseProduct.setPrice(new BigDecimal("100.00"));
    baseProduct.setBrand(brand);
    baseProduct.setCategory(category);
  }

  @Test
  void createProduct_success() {
    // Arrange
    when(productGateway.findProductByName(baseProduct.getName())).thenReturn(Mono.empty());
    when(brandGateway.findById(1L)).thenReturn(Mono.just(baseProduct.getBrand()));
    when(categoryGateway.findById(2L)).thenReturn(Mono.just(baseProduct.getCategory()));

    // creamos una copia simulada del producto persistido -> con id
    Product productSaved = new Product();
    productSaved.setProductId(100L);
    productSaved.setName(baseProduct.getName());
    productSaved.setDescription(baseProduct.getDescription());
    productSaved.setPrice(baseProduct.getPrice());
    productSaved.setStock(baseProduct.getStock());
    productSaved.setMinStock(baseProduct.getMinStock());
    productSaved.setBrand(baseProduct.getBrand());
    productSaved.setCategory(baseProduct.getCategory());
    productSaved.setCreatedAt(baseProduct.getCreatedAt());
    productSaved.setUpdatedAt(baseProduct.getUpdatedAt());
    productSaved.setAvailableStock(baseProduct.getAvailableStock());
    productSaved.setIsActive(baseProduct.getIsActive());

    when(productGateway.createProduct(any(Product.class))).thenReturn(Mono.just(productSaved));

    StepVerifier.create(useCase.execute(baseProduct))// Sirve para verificar qué devuelve el Mono.
            .expectNextMatches(product -> product.getProductId() != null && product.getName().equals("Producto A"))
            .verifyComplete(); // Crea un “verificador” para inspeccionar la secuencia reactiva

    // queremos asegurarnos de que el caso de uso usó correctamente sus dependencias.
    verify(productGateway).findProductByName("Producto A");
    verify(brandGateway).findById(1L);
    verify(categoryGateway).findById(2L);
    verify(productGateway).createProduct(baseProduct);
  }

  @Test // validamos el caso donde creamos un producto con el precio null
  void validation_price_invalid() {
    baseProduct.setPrice(null);
    StepVerifier.create(useCase.execute(baseProduct))
            .expectErrorSatisfies(err -> {
              assert err instanceof ValidationException;
              assert err.getMessage().contains("Product price");
            })
            .verify();
  }

  @Test
  // validamos el caso donde creamos un producto con el nombre vacio
  void validation_name_empty() {
    baseProduct.setName("  ");
    StepVerifier.create(useCase.execute(baseProduct))
            .expectErrorMatches(throwable -> throwable instanceof ValidationException
                    && throwable.getMessage().contains("Product name"))
            .verify();
  }

  @Test
  void validation_description_empty() {
    baseProduct.setDescription("");
    StepVerifier.create(useCase.execute(baseProduct))
            .expectErrorMatches(t -> t instanceof ValidationException && t.getMessage().contains("Product description"))
            .verify();
  }

  @Test
  void validation_brand_null() {
    baseProduct.setBrand(null);
    StepVerifier.create(useCase.execute(baseProduct))
            .expectErrorMatches(t -> t instanceof ValidationException && t.getMessage().contains("Product brand"))
            .verify();
  }

  @Test
  void validation_category_null() {
    baseProduct.setCategory(null);
    StepVerifier.create(useCase.execute(baseProduct))
            .expectErrorMatches(t -> t instanceof ValidationException && t.getMessage().contains("Product category"))
            .verify();
  }

  @Test
  void validation_stock_invalid() {
    baseProduct.setStock(0);
    StepVerifier.create(useCase.execute(baseProduct))
            .expectErrorMatches(t -> t instanceof ValidationException && t.getMessage().contains("Product stock"))
            .verify();
  }

  @Test
  void validation_minStock_invalid() {
    baseProduct.setMinStock(0);
    StepVerifier.create(useCase.execute(baseProduct))
            .expectErrorMatches(t -> t instanceof ValidationException && t.getMessage().contains("Product min stock"))
            .verify();
  }

  @Test
  void business_product_exists() {
    Product existing = new Product();
    existing.setProductId(999L);
    existing.setName(baseProduct.getName());

    Brand brand = new Brand();
    brand.setBrandId(1L);
    brand.setName("Marca A");

    when(productGateway.findProductByName(baseProduct.getName())).thenReturn(Mono.just(existing));

    when(brandGateway.findById(1L))
            .thenReturn(Mono.just(brand));

    StepVerifier.create(useCase.execute(baseProduct))
            .expectErrorMatches(t -> t instanceof BusinessException && t.getMessage().contains("already exists"))
            .verify();

    verify(productGateway).findProductByName(baseProduct.getName());
  }

  @Test
  void not_found_brand() {
    when(productGateway.findProductByName(baseProduct.getName())).thenReturn(Mono.empty());
    when(brandGateway.findById(1L)).thenReturn(Mono.empty());

    StepVerifier.create(useCase.execute(baseProduct))
            .expectErrorMatches(t -> t instanceof NotFoundException && t.getMessage().contains("Brand with id"))
            .verify();

    verify(productGateway).findProductByName(baseProduct.getName());
    verify(brandGateway).findById(1L);
  }

  @Test
  void not_found_category() {
    when(productGateway.findProductByName(baseProduct.getName())).thenReturn(Mono.empty());
    when(brandGateway.findById(1L)).thenReturn(Mono.just(baseProduct.getBrand()));
    when(categoryGateway.findById(2L)).thenReturn(Mono.empty());

    StepVerifier.create(useCase.execute(baseProduct))
            .expectErrorMatches(t -> t instanceof NotFoundException && t.getMessage().contains("Category with id"))
            .verify();

    verify(productGateway).findProductByName(baseProduct.getName());
    verify(brandGateway).findById(1L);
    verify(categoryGateway).findById(2L);
  }



}
