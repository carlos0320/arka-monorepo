package com.arka.inventorymcsv.infrastructure.controllers;

import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.domain.usecase.product.*;
import com.arka.inventorymcsv.infrastructure.controllers.dto.BatchProductRequest;
import com.arka.inventorymcsv.infrastructure.controllers.dto.ProductDto;
import com.arka.inventorymcsv.infrastructure.controllers.dto.ResponseDto;
import com.arka.inventorymcsv.infrastructure.controllers.dto.StockOperationRequestDto;
import com.arka.inventorymcsv.infrastructure.controllers.mapper.ProductDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@RestController
@RequestMapping("/api/inventory/products")
public class ProductController {

  private final CreateProductUseCase createProductUseCase;
  private final ListAllProductsUseCase listAllProductsUseCase;
  private final UpdateProductUseCase updateProductUseCase;
  private final GetProductDetailUseCase getProductDetailUseCase;
  private final GetProductsByIdsUseCase batchProductsUseCase;

  private final ReserveStockUseCase reserveStockUseCase;
  private final ReleaseStockUseCase releaseStockUseCase;
  private final ConfirmStockUseCase confirmStockUseCase;
  private final RestockUseCase restockUseCase;

  private final ListLowStockProductsUseCase listLowStockProducts;


  public ProductController(CreateProductUseCase createProductUseCase,
                           ListAllProductsUseCase listAllProductsUseCase,
                           UpdateProductUseCase updateProductUseCase,
                           GetProductDetailUseCase getProductDetailUseCase, GetProductsByIdsUseCase batchProductsUseCase,
                           ReserveStockUseCase reserveStockUseCase,
                           ReleaseStockUseCase releaseStockUseCase,
                           ConfirmStockUseCase confirmStockUseCase,
                           RestockUseCase restockUseCase, ListLowStockProductsUseCase listLowStockProducts
  ) {
    this.createProductUseCase = createProductUseCase;
    this.listAllProductsUseCase = listAllProductsUseCase;
    this.updateProductUseCase = updateProductUseCase;
    this.getProductDetailUseCase = getProductDetailUseCase;
    this.batchProductsUseCase = batchProductsUseCase;
    this.reserveStockUseCase = reserveStockUseCase;
    this.releaseStockUseCase = releaseStockUseCase;
    this.confirmStockUseCase = confirmStockUseCase;
    this.restockUseCase = restockUseCase;
    this.listLowStockProducts = listLowStockProducts;
  }

  @PostMapping("/admin/create")
  public Mono<ResponseEntity<ResponseDto>> createProduct(
          @RequestHeader("X-User-roles") String roles,
          @Valid @RequestBody Mono<ProductDto> productDtoMono) {

    if (!roles.contains("ADMIN")){
      return forbiddenErrorMessage();
    }

    return productDtoMono
            .flatMap(product -> {
              Product productToSave = ProductDtoMapper.toDomain(product);
              return createProductUseCase.execute(productToSave)
                      .map(productSaved -> {
                        ResponseDto responseDto = new ResponseDto();
                        Map<String, Object> data = new HashMap<>();
                        data.put("product", productSaved);
                        responseDto.setData(data);
                        responseDto.setStatus(HttpStatus.CREATED);

                        return ResponseEntity.ok().body(responseDto);
                      });
            });
  }

  @GetMapping
  public Mono<ResponseEntity<ResponseDto>> listAllProducts() {
    return listAllProductsUseCase.execute()
            .collectList()
            .map(product -> {
              ResponseDto responseDto = new ResponseDto();
              Map<String, Object> data = new HashMap<>();
              data.put("products", product);
              responseDto.setData(data);
              responseDto.setStatus(HttpStatus.CREATED);

              return ResponseEntity.ok().body(responseDto);
            });
  }

  @PatchMapping("/admin")
  public Mono<ResponseEntity<ResponseDto>> updateProduct(
          @RequestHeader("X-User-roles") String roles,
          @RequestBody Mono<ProductDto> productDtoMono) {

    if (!roles.contains("ADMIN")){
      return forbiddenErrorMessage();
    }

    return productDtoMono
            .flatMap(productMono -> {
              Product productToUpdate = ProductDtoMapper.toDomain(productMono);
              return updateProductUseCase.execute(productToUpdate)
                      .then(Mono.fromSupplier(() -> {
                        ResponseDto responseDto = new ResponseDto();
                        Map<String, Object> data = new HashMap<>();
                        data.put("message", "Producto actualizado correctamente");
                        responseDto.setData(data);
                        responseDto.setStatus(HttpStatus.OK);
                        return ResponseEntity.ok(responseDto);
                      }));

            });
  }

  @GetMapping("/details/{productId}")
  public Mono<ResponseEntity<ResponseDto>> getProductDetail(@PathVariable("productId") String productId) {
    return getProductDetailUseCase.execute(Long.valueOf(productId))
            .map(product -> {
              ResponseDto responseDto = new ResponseDto();
              Map<String, Object> data = new HashMap<>();
              data.put("product", product);
              responseDto.setData(data);
              responseDto.setStatus(HttpStatus.OK);
              return ResponseEntity.ok(responseDto);
            });
  }

  @GetMapping("/admin/low-stock-products")
  public Mono<ResponseEntity<ResponseDto>> getLowStockProducts(@RequestHeader("X-User-roles") String roles) {
    if (!roles.contains("ADMIN")){
      return forbiddenErrorMessage();
    }
    return listLowStockProducts.execute()
            .collectList()
            .map(products ->  {
              ResponseDto responseDto = new ResponseDto();
              Map<String, Object> data = new HashMap<>();
              data.put("products", products);
              responseDto.setData(data);
              responseDto.setStatus(HttpStatus.OK);
              return ResponseEntity.ok(responseDto);
            });
  }

  @PostMapping("/reserve-stock")
  public Mono<ResponseEntity<ResponseDto>> reserveStock(@RequestBody Mono<StockOperationRequestDto> requestMono) {
    return getResponseEntityMono(requestMono, reserveStockUseCase::execute);
  }


  @PostMapping("/release-stock")
  public Mono<ResponseEntity<ResponseDto>> releaseStock(@RequestBody Mono<StockOperationRequestDto> requestMono) {
    return getResponseEntityMono(requestMono, releaseStockUseCase::execute);
  }

  @PostMapping("/confirm-stock")
  public Mono<ResponseEntity<ResponseDto>> confirmStock(@RequestBody Mono<StockOperationRequestDto> requestMono) {
    return getResponseEntityMono(requestMono, confirmStockUseCase::execute);
  }

  @PostMapping("/admin/re-stock")
  public Mono<ResponseEntity<ResponseDto>> updateStock(
          @RequestHeader("X-User-roles") String roles,
          @RequestBody Mono<StockOperationRequestDto> requestMono) {
    if (!roles.contains("ADMIN")){
      return forbiddenErrorMessage();
    }
    return getResponseEntityMono(requestMono, restockUseCase::execute);
  }

  @PostMapping("/batch")
  public Mono<ResponseEntity<ResponseDto>> getProductsByIds(@RequestBody Mono<BatchProductRequest> requestMono) {
    return requestMono.flatMap(request ->
            batchProductsUseCase.execute(request.getProductIds())
                    .collectList()
                    .map(products -> {
                      ResponseDto responseDto = new ResponseDto();
                      Map<String, Object> data = new HashMap<>();
                      data.put("products", products);
                      responseDto.setData(data);
                      responseDto.setStatus(HttpStatus.OK);
                      return ResponseEntity.ok().body(responseDto);
                    })
    );
  }

  private Mono<ResponseEntity<ResponseDto>> forbiddenErrorMessage(){
    ResponseDto<String> responseDto = new ResponseDto();
    responseDto.setData("Forbidden: Admin only");
    responseDto.setStatus(HttpStatus.FORBIDDEN);
    return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto));
  }


  private Mono<ResponseEntity<ResponseDto>> getResponseEntityMono(Mono<StockOperationRequestDto> requestMono, BiFunction<Long,Integer, Mono<Product>> stockOperationFunction) {
    return requestMono
            .flatMap(request ->
                    stockOperationFunction.apply(request.getProductId(), request.getQuantity())
            )
            .map(product ->{
              ResponseDto responseDto = new ResponseDto();
              Map<String, Object> data = new HashMap<>();
              data.put("product", product);
              responseDto.setData(data);
              responseDto.setStatus(HttpStatus.OK);
              return ResponseEntity.ok(responseDto);
            })
            .onErrorResume(e ->{
              ResponseDto responseDto = new ResponseDto();
              Map<String, Object> data = new HashMap<>();
              data.put("error", e.getMessage());
              responseDto.setData(data);
              responseDto.setStatus(HttpStatus.BAD_REQUEST);
              return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto));
            });
  }


}
