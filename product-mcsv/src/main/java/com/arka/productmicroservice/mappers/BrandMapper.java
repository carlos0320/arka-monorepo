package com.arka.productmicroservice.mappers;

import com.arka.productmicroservice.dtos.BrandDto;
import com.arka.productmicroservice.entities.Brand;

public class BrandMapper {
   public static Brand toBrand(Brand brand, BrandDto brandDto){
      brand.setName(brandDto.getName());
      brand.setLogo(brandDto.getLogo());
      return brand;
   }

   public static  BrandDto toBrandDto(Brand brand, BrandDto brandDto){
      brandDto.setName(brand.getName());
      brandDto.setLogo(brand.getLogo());
      return  brandDto;
   }
}
