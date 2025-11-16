package com.arka.inventorymcsv.domain.model.gateway;

import com.arka.inventorymcsv.domain.model.Brand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface BrandGateway {
  Mono<Brand> saveBrand(Brand brand);
  Flux<Brand> getAllBrands();

  Mono<Brand> findById(Long brandId);
}
