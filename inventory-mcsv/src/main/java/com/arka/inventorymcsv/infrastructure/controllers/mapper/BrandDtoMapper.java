package com.arka.inventorymcsv.infrastructure.controllers.mapper;

import com.arka.inventorymcsv.domain.model.Brand;
import com.arka.inventorymcsv.infrastructure.adapters.entity.BrandEntity;
import com.arka.inventorymcsv.infrastructure.controllers.dto.BrandDto;
import lombok.Data;

@Data
public class BrandDtoMapper {
  public static BrandDto toDto(Brand brand) {
    BrandDto brandDto = new BrandDto();
    brandDto.setBrandId(brand.getBrandId());
    brandDto.setName(brand.getName());
    brandDto.setLogo(brand.getLogo());
    return brandDto;
  }

  public  static Brand toDomain(BrandDto brandDto) {
    Brand brand = new Brand();
    brand.setBrandId(brandDto.getBrandId());
    brand.setLogo(brandDto.getLogo());
    brand.setName(brandDto.getName());
    return brand;
  }
}
