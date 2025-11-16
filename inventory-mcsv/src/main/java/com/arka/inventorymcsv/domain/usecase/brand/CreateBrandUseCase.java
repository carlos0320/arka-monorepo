package com.arka.inventorymcsv.domain.usecase.brand;

import com.arka.inventorymcsv.domain.exceptions.ValidationException;
import com.arka.inventorymcsv.domain.model.Brand;
import com.arka.inventorymcsv.domain.model.gateway.BrandGateway;
import lombok.Data;
import reactor.core.publisher.Mono;

@Data
public class CreateBrandUseCase {
  private final BrandGateway brandGateway;

  public Mono<Brand> execute(Brand brand){

    if (brand.getName() == null || brand.getName().trim().isEmpty()){
      return Mono.error(new ValidationException("Brand name is required"));
    }

    return brandGateway.saveBrand(brand);
  }

}
