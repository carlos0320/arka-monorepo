package com.arka.inventorymcsv.domain.usecase.product;

import com.arka.inventorymcsv.domain.exceptions.BusinessException;
import com.arka.inventorymcsv.domain.exceptions.NotFoundException;
import com.arka.inventorymcsv.domain.exceptions.ValidationException;
import com.arka.inventorymcsv.domain.model.Brand;
import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.domain.model.gateway.BrandGateway;
import com.arka.inventorymcsv.domain.model.gateway.CategoryGateway;
import com.arka.inventorymcsv.domain.model.gateway.ProductGateway;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class CreateProductUseCase {
  private final ProductGateway productGateway;
  private final BrandGateway brandGateway;
  private final CategoryGateway categoryGateway;

  public CreateProductUseCase(ProductGateway productGateway, BrandGateway brandGateway, CategoryGateway categoryGateway) {
    this.productGateway = productGateway;
    this.brandGateway = brandGateway;
    this.categoryGateway = categoryGateway;
  }

  public Mono<Product> execute(Product product) {
    if (product.getPrice() == null || product.getPrice().signum() <= 0){
      return Mono.error(new ValidationException("Product price must be greater than 0"));
    }

    if (product.getName() == null || product.getName().trim().isEmpty()){
      return Mono.error(new ValidationException("Product name is required and not be empty"));
    }

    if(product.getDescription() == null || product.getDescription().trim().isEmpty()){
      return Mono.error(new ValidationException("Product description is required and not be empty"));
    }

    if(product.getBrand() == null){
      return Mono.error(new ValidationException("Product brand is required and not be empty"));
    }

    if(product.getCategory() == null){
      return Mono.error(new ValidationException("Product category is required and not be empty"));
    }

    if(product.getStock() == null || product.getStock() <= 0){
      return Mono.error(new ValidationException("Product stock is required and must be greater than 0"));
    }

    if (product.getMinStock() == null || product.getMinStock() <= 0){
      return Mono.error(new ValidationException("Product min stock is required and must be greater than 0"));
    }

    return productGateway.findProductByName(product.getName())
            .flatMap(existing -> Mono.<Product>error(new BusinessException("Product with that name already exists")))
            .switchIfEmpty(
                    brandGateway.findById(product.getBrand().getBrandId())
                            .switchIfEmpty(Mono.error(new NotFoundException("Brand with id " + product.getBrand().getBrandId() + " not found")))
                            .flatMap(brand ->
                                    categoryGateway.findById(product.getCategory().getCategoryId())
                                            .switchIfEmpty(Mono.error(new NotFoundException("Category with id " + product.getCategory().getCategoryId() + " not found")))
                                            .flatMap(category -> productGateway.createProduct(product))
                            )
            );
  }
}
