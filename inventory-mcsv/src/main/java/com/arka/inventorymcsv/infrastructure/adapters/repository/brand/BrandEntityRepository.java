package com.arka.inventorymcsv.infrastructure.adapters.repository.brand;

import com.arka.inventorymcsv.infrastructure.adapters.entity.BrandEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface BrandEntityRepository extends ReactiveCrudRepository<BrandEntity, Long> {
  Mono<BrandEntity> findBrandEntityByBrandId(Long brandId);
}
