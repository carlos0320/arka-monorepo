package com.arka.inventorymcsv.infrastructure.controllers;

import com.arka.inventorymcsv.domain.model.Brand;
import com.arka.inventorymcsv.domain.usecase.brand.CreateBrandUseCase;
import com.arka.inventorymcsv.domain.usecase.brand.GetAllBrandUseCase;
import com.arka.inventorymcsv.infrastructure.controllers.dto.BrandDto;
import com.arka.inventorymcsv.infrastructure.controllers.dto.ResponseDto;
import com.arka.inventorymcsv.infrastructure.controllers.mapper.BrandDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory/brand")
public class BrandController {

  private final CreateBrandUseCase createBrandUseCase;
  private final GetAllBrandUseCase getAllBrandUseCase;

  public BrandController(CreateBrandUseCase createBrandUseCase, GetAllBrandUseCase getAllBrandUseCase) {
    this.createBrandUseCase = createBrandUseCase;
    this.getAllBrandUseCase = getAllBrandUseCase;
  }

  @PostMapping("/admin")
  public Mono<ResponseEntity<ResponseDto>> createBrand(@RequestHeader("X-User-Id") String userId,
                                                       @RequestHeader("X-User-Roles") String roles,
                                                       @Valid @RequestBody Mono<BrandDto> brandDtoMono){

    if (!roles.contains("ADMIN")){
      ResponseDto<String> responseDto = new ResponseDto();
      responseDto.setData("Forbidden: Admin only");
      responseDto.setStatus(HttpStatus.FORBIDDEN);
      return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto));
    }

    return brandDtoMono
            .flatMap(brandToSave ->{
              Brand brand = BrandDtoMapper.toDomain(brandToSave);
              return createBrandUseCase.execute(brand)
                      .map(brandSaved ->{
                        Map<String, Object> data = new HashMap<>();
                        data.put("brand", brandSaved);
                        ResponseDto responseDto = new ResponseDto();
                        responseDto.setData(data);
                        responseDto.setStatus(HttpStatus.CREATED);
                        return ResponseEntity.ok().body(responseDto);
                      });
            });
  }

  @GetMapping
  public Mono<ResponseEntity<ResponseDto>> getAllBrands(){
    return getAllBrandUseCase.execute()
            .collectList()
            .map(brandStored -> {
              Map<String, Object> data = new HashMap<>();
              data.put("brand", brandStored);
              ResponseDto responseDto = new ResponseDto();
              responseDto.setData(data);
              responseDto.setStatus(HttpStatus.OK);
              return ResponseEntity.ok().body(responseDto);
            });
  }
}
