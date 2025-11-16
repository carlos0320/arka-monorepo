package com.arka.inventorymcsv.infrastructure.adapters.mappers;

import com.arka.inventorymcsv.domain.model.Brand;
import com.arka.inventorymcsv.infrastructure.adapters.entity.BrandEntity;
import lombok.Data;

@Data
public class BrandMapper {
  public static BrandEntity toEntity(Brand brand) {
    BrandEntity brandEntity = new BrandEntity();
    brandEntity.setBrandId(brand.getBrandId());
    brandEntity.setName(brand.getName());
    brandEntity.setLogo(brand.getLogo());
    return brandEntity;
  }

  public static Brand toDomain(BrandEntity brandEntity) {
    Brand brand = new Brand();
    brand.setBrandId(brandEntity.getBrandId());
    brand.setName(brandEntity.getName());
    brand.setLogo(brandEntity.getLogo());
    return brand;
  }
}
