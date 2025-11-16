package com.arka.inventorymcsv.application;

import com.arka.inventorymcsv.domain.model.gateway.BrandGateway;
import com.arka.inventorymcsv.domain.model.gateway.CategoryGateway;
import com.arka.inventorymcsv.domain.model.gateway.ProductGateway;
import com.arka.inventorymcsv.domain.usecase.brand.CreateBrandUseCase;
import com.arka.inventorymcsv.domain.usecase.brand.GetAllBrandUseCase;
import com.arka.inventorymcsv.domain.usecase.category.CreateCategoryUseCase;
import com.arka.inventorymcsv.domain.usecase.category.GetAllCategoriesUseCase;
import com.arka.inventorymcsv.domain.usecase.product.*;
import com.arka.inventorymcsv.infrastructure.adapters.repository.brand.BrandEntityRepository;
import com.arka.inventorymcsv.infrastructure.adapters.repository.brand.BrandGatewayImpl;
import com.arka.inventorymcsv.infrastructure.adapters.repository.category.CategoryEntityRepository;
import com.arka.inventorymcsv.infrastructure.adapters.repository.category.CategoryGatewayImpl;
import com.arka.inventorymcsv.infrastructure.adapters.repository.product.ProductEntityRepository;
import com.arka.inventorymcsv.infrastructure.adapters.repository.product.ProductGatewayRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryConfig {

  @Bean
  public BrandGateway brandGateway(BrandEntityRepository brandEntityRepository) {
    return new BrandGatewayImpl(brandEntityRepository);
  }

  @Bean
  public CategoryGateway categoryGateway(CategoryEntityRepository categoryEntityRepository) {
    return new CategoryGatewayImpl(categoryEntityRepository);
  }

  @Bean
  public ProductGateway productGateway(ProductEntityRepository productEntityRepository, CategoryEntityRepository categoryEntityRepository, BrandEntityRepository brandEntityRepository) {
    return new ProductGatewayRepository(productEntityRepository, categoryEntityRepository, brandEntityRepository);
  }

  @Bean
  public CreateBrandUseCase createBrandUseCase(BrandGateway brandGateway) {
    return new CreateBrandUseCase(brandGateway);
  }

  @Bean
  public CreateCategoryUseCase createCategoryUseCase(CategoryGateway categoryGateway) {
    return new CreateCategoryUseCase(categoryGateway);
  }

  @Bean
  public GetAllBrandUseCase getAllBrandUseCase(BrandGateway brandGateway) {
    return new GetAllBrandUseCase(brandGateway);
  }

  @Bean
  public GetAllCategoriesUseCase getAllCategoriesUseCase(CategoryGateway categoryGateway) {
    return new GetAllCategoriesUseCase(categoryGateway);
  }

  @Bean
  public CreateProductUseCase createProductUseCase(ProductGateway productGateway, BrandGateway brandGateway, CategoryGateway categoryGateway) {
    return new CreateProductUseCase(productGateway, brandGateway, categoryGateway);
  }

  @Bean
  public ListAllProductsUseCase listAllProductsUseCase(ProductGateway productGateway) {
    return new ListAllProductsUseCase(productGateway);
  }

  @Bean
  public UpdateProductUseCase updateProductUseCase(ProductGateway productGateway, BrandGateway brandGateway, CategoryGateway categoryGateway) {
    return new UpdateProductUseCase(productGateway,brandGateway,categoryGateway);
  }

  @Bean
  public GetProductDetailUseCase getProductDetailUseCase(ProductGateway productGateway) {
    return new GetProductDetailUseCase(productGateway);
  }

  @Bean
  public ReserveStockUseCase reserveStockUseCase(ProductGateway productGateway) {
    return new ReserveStockUseCase(productGateway);
  }

  @Bean
  public ReduceStockUseCase reduceStockUseCase(ProductGateway productGateway) {
    return new ReduceStockUseCase(productGateway);
  }

  @Bean
  public ReleaseStockUseCase releaseStockUseCase(ProductGateway productGateway) {
    return new ReleaseStockUseCase(productGateway);
  }

  @Bean
  public ConfirmStockUseCase confirmStockUseCase(ProductGateway productGateway) {
    return new ConfirmStockUseCase(productGateway);
  }

  @Bean
  public RestockUseCase restockUseCase(ProductGateway productGateway) {
    return new RestockUseCase(productGateway);
  }

  @Bean
  public GetProductsByIdsUseCase getProductsByIdsUseCase(ProductGateway productGateway) {
    return new GetProductsByIdsUseCase(productGateway);
  }

  @Bean
  public ListLowStockProductsUseCase listLowStockProductsUseCase(ProductGateway productGateway) {
    return new ListLowStockProductsUseCase(productGateway);
  }


}
