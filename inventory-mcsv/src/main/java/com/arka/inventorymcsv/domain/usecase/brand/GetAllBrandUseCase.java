package com.arka.inventorymcsv.domain.usecase.brand;

import com.arka.inventorymcsv.domain.model.Brand;
import com.arka.inventorymcsv.domain.model.gateway.BrandGateway;
import reactor.core.publisher.Flux;

public class GetAllBrandUseCase {
  private final BrandGateway brandGateway;

  public GetAllBrandUseCase(BrandGateway brandGateway) {
    this.brandGateway = brandGateway;
  }

  public Flux<Brand> execute(){
    return brandGateway.getAllBrands();
  }
}
