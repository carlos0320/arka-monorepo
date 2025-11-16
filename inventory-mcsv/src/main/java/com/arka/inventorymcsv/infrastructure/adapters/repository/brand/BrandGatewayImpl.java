package com.arka.inventorymcsv.infrastructure.adapters.repository.brand;

import com.arka.inventorymcsv.domain.model.Brand;
import com.arka.inventorymcsv.domain.model.gateway.BrandGateway;
import com.arka.inventorymcsv.infrastructure.adapters.entity.BrandEntity;
import com.arka.inventorymcsv.infrastructure.adapters.mappers.BrandMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class BrandGatewayImpl implements BrandGateway {

  private final BrandEntityRepository brandEntityRepository;

  public BrandGatewayImpl(BrandEntityRepository brandEntityRepository) {
    this.brandEntityRepository = brandEntityRepository;
  }

  @Override
  public Mono<Brand> saveBrand(Brand brand) {
    BrandEntity brandEntity = BrandMapper.toEntity(brand);

    return brandEntityRepository.save(brandEntity)
            .map(BrandMapper::toDomain);
  }

  @Override
  public Flux<Brand> getAllBrands() {
    return brandEntityRepository.findAll()
            .map(BrandMapper::toDomain);

  }

  @Override
  public Mono<Brand> findById(Long brandId) {
    return brandEntityRepository.findBrandEntityByBrandId(brandId)
            .map(BrandMapper::toDomain);
  }
}
