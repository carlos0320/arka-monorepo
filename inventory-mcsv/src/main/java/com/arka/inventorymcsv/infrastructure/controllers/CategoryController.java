package com.arka.inventorymcsv.infrastructure.controllers;

import com.arka.inventorymcsv.domain.model.Category;
import com.arka.inventorymcsv.domain.usecase.category.CreateCategoryUseCase;
import com.arka.inventorymcsv.domain.usecase.category.GetAllCategoriesUseCase;
import com.arka.inventorymcsv.infrastructure.adapters.mappers.CategoryMapper;
import com.arka.inventorymcsv.infrastructure.controllers.dto.CategoryDto;
import com.arka.inventorymcsv.infrastructure.controllers.dto.ResponseDto;
import com.arka.inventorymcsv.infrastructure.controllers.mapper.CategoryDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory/category")
public class CategoryController {
  private final CreateCategoryUseCase createCategoryUseCase;
  private final GetAllCategoriesUseCase getAllCategoriesUseCase;

  public CategoryController(CreateCategoryUseCase createCategoryUseCase, GetAllCategoriesUseCase getAllCategoriesUseCase) {
    this.createCategoryUseCase = createCategoryUseCase;
    this.getAllCategoriesUseCase = getAllCategoriesUseCase;
  }

  @PostMapping("/admin")
  public Mono<ResponseEntity<ResponseDto>> createCategory(
          @RequestHeader("X-User-Id") String userId,
          @RequestHeader("X-User-roles") String roles,
          @RequestBody Mono<CategoryDto> categoryDtoMono){

    if (!roles.contains("ADMIN")){
      ResponseDto<String> responseDto = new ResponseDto();
      responseDto.setData("Forbidden: Admin only");
      responseDto.setStatus(HttpStatus.FORBIDDEN);
      return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto));
    }

    return categoryDtoMono
            .flatMap(categoryToSave ->{
              Category category = CategoryDtoMapper.toDomain(categoryToSave);
              Map<String, Object> data = new HashMap<>();
              return createCategoryUseCase.execute(category)
                      .map(categorySaved ->{
                        CategoryDto categoryDto = CategoryDtoMapper.toDto(categorySaved);
                        ResponseDto responseDto = new ResponseDto();
                        responseDto.setStatus(HttpStatus.CREATED);
                        data.put("category", categoryDto);
                        responseDto.setData(data);
                        return ResponseEntity.ok().body(responseDto);
                      });
            });
  }

  @GetMapping
  public Mono<ResponseEntity<ResponseDto>> getAllCategories(){
    return getAllCategoriesUseCase.execute()
            .map(CategoryDtoMapper::toDto)
            .collectList()
            .map(categoryDto ->{
              Map<String, Object> data = new HashMap<>();
              data.put("category", categoryDto);
              ResponseDto responseDto = new ResponseDto();
              responseDto.setStatus(HttpStatus.OK);
              responseDto.setData(data);
              return ResponseEntity.ok().body(responseDto);
            });
  }
}
