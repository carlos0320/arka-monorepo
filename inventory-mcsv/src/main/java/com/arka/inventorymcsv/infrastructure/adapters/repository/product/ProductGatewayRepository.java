package com.arka.inventorymcsv.infrastructure.adapters.repository.product;

import com.arka.inventorymcsv.domain.model.Brand;
import com.arka.inventorymcsv.domain.model.Category;
import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.domain.model.gateway.ProductGateway;
import com.arka.inventorymcsv.infrastructure.adapters.entity.BrandEntity;
import com.arka.inventorymcsv.infrastructure.adapters.entity.CategoryEntity;
import com.arka.inventorymcsv.infrastructure.adapters.entity.ProductEntity;
import com.arka.inventorymcsv.infrastructure.adapters.mappers.BrandMapper;
import com.arka.inventorymcsv.infrastructure.adapters.mappers.CategoryMapper;
import com.arka.inventorymcsv.infrastructure.adapters.mappers.ProductMapper;
import com.arka.inventorymcsv.infrastructure.adapters.repository.brand.BrandEntityRepository;
import com.arka.inventorymcsv.infrastructure.adapters.repository.category.CategoryEntityRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class ProductGatewayRepository implements ProductGateway {
  private final ProductEntityRepository productEntityRepository;

  private final CategoryEntityRepository categoryEntityRepository;
  private final BrandEntityRepository brandEntityRepository;

  public ProductGatewayRepository(ProductEntityRepository productEntityRepository, CategoryEntityRepository categoryEntityRepository, BrandEntityRepository brandEntityRepository) {
    this.productEntityRepository = productEntityRepository;
    this.categoryEntityRepository = categoryEntityRepository;
    this.brandEntityRepository = brandEntityRepository;
  }

  @Override
  public Mono<Product> createProduct(Product product) {
    ProductEntity productEntity = ProductMapper.toEntity(product);
    Mono<ProductEntity> productSaved = productEntityRepository.save(productEntity);
    Mono<Brand> brandSaved = brandEntityRepository.findBrandEntityByBrandId(productEntity.getBrandId())
            .map(BrandMapper::toDomain);
    Mono<Category> categorySaved =categoryEntityRepository.findCategoryEntitiesByCategoryId(productEntity.getCategoryId())
            .map(CategoryMapper::toDomain);

    return Mono.zip(productSaved, categorySaved, brandSaved)
            .map(tuple -> ProductMapper.toDomain(tuple.getT1(), tuple.getT2(), tuple.getT3()));

  }

  @Override
  public Mono<Product> findProductByName(String name) {
    return productEntityRepository.findProductByName(name)
      .flatMap(productEntity -> {
        return getMono(productEntity);
      }); // convert entity → domain
  }

  @Override
  public Mono<Product> findProductById(Long productId) {
    return productEntityRepository.findProductByProductId(productId)
            .flatMap(productEntity -> {
              return getMono(productEntity);
            })
            .switchIfEmpty(Mono.error(new RuntimeException("Product not found")));
  }


  @Override
  public Flux<Product> listAllProducts() {
    return productEntityRepository.findProductEntitiesByIsActiveTrue()
            .flatMap(productEntity -> {
              return getMono(productEntity);

            });
  }

  @Override
  public Mono<Void> updateProduct(Product product) {
    ProductEntity productEntity = ProductMapper.toEntity(product);

    return productEntityRepository.findProductByProductId(productEntity.getProductId())
            .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
            .flatMap(productStored -> {
              if (product.getDescription() != null) {
                productStored.setDescription(product.getDescription());
              }

              if (product.getName() != null) {
                productStored.setName(product.getName());
              }
              if (product.getPrice() != null) {
                productStored.setPrice(product.getPrice());
              }
              if (product.getImageUrl() != null) {
                productStored.setImageUrl(product.getImageUrl());
              }
              if (product.getBrand() != null) {
                productStored.setBrandId(product.getBrand().getBrandId());
              }

              if (product.getCategory() != null) {
                productStored.setCategoryId(product.getCategory().getCategoryId());
              }

              if (product.getMinStock() != null) {
                productStored.setMinStock(product.getMinStock());
              }

              if (product.getAvailableStock() != null) {
                productStored.setAvailableStock(product.getAvailableStock());
              }

              if (product.getStock() != null) {
                productStored.setStock(product.getStock());
              }

              if (product.getReservedStock() != null) {
                productStored.setReservedStock(product.getReservedStock());
              }

              return productEntityRepository.save(productStored);
            })
            .then();
  }

  @Override
  public Flux<Product> getProductsByIds(List<Long> ids) {
    return productEntityRepository.findProductEntitiesByProductIdIsIn(ids)
            .flatMap(productEntity -> {
              return getMono(productEntity);
            });
  }

  @Override
  public Flux<Product> getLowStockProducts() {
    return productEntityRepository.findLowStockProducts()
            .flatMap(productEntity -> {
              return getMono(productEntity);
            });
  }


  private Mono<Product> getMono(ProductEntity existingProduct) {
    Mono<Brand> brandData = brandEntityRepository.findBrandEntityByBrandId(existingProduct.getBrandId())
            .map(BrandMapper::toDomain);

    Mono<Category> categoryData = categoryEntityRepository.findCategoryEntitiesByCategoryId(existingProduct.getCategoryId())
            .map(CategoryMapper::toDomain);

    return Mono.zip(Mono.just(existingProduct), categoryData, brandData)
            .map(tuple-> ProductMapper.toDomain(tuple.getT1(), tuple.getT2(), tuple.getT3()));
  }

}
