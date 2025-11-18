package com.arka.inventorymcsv.domain.usecase;


import com.arka.inventorymcsv.domain.exceptions.BusinessException;
import com.arka.inventorymcsv.domain.exceptions.NotFoundException;
import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.domain.model.gateway.BrandGateway;
import com.arka.inventorymcsv.domain.model.gateway.CategoryGateway;
import com.arka.inventorymcsv.domain.model.gateway.ProductGateway;
import com.arka.inventorymcsv.domain.usecase.product.ConfirmStockUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConfirmStockUseCaseTest {
  @Mock
  ProductGateway productGateway;
  ConfirmStockUseCase useCase;


  @BeforeEach
  void setUp() {
    useCase = new ConfirmStockUseCase(productGateway);
  }

  // deberia fallar si el producto no existe
  @Test
  void shouldThrowNotFoundException_WhenProductDoesNotExist() {
    Long productId = 1L;

    when(productGateway.findProductById(productId))
            .thenReturn(Mono.empty());

    StepVerifier.create(useCase.execute(productId, 10))
            .expectErrorMatches(ex -> ex instanceof NotFoundException &&
                    ex.getMessage().equals("Product not found"))
            .verify();

    verify(productGateway).findProductById(productId);
  }

  // falla si quiero confirmar más de lo reservado de ese producto
  @Test
  void shouldThrowBusinessException_WhenReservedStockIsLess() {
    Long productId = 1L;

    Product product = new Product();
    product.setStock(50);
    product.setReservedStock(5);
    product.setAvailableStock(45);

    when(productGateway.findProductById(productId))
            .thenReturn(Mono.just(product));

    StepVerifier.create(useCase.execute(productId, 10))
            .expectErrorMatches(ex -> ex instanceof BusinessException &&
                    ex.getMessage().equals("Requested quantity exceeds reserved stock"))
            .verify();

    verify(productGateway).findProductById(productId);
  }


  // caso de exito -> quiero confirmar 10 de los 20 reservados
  @Test
  void shouldUpdateProductSuccessfully_WhenStockIsValid() {
    Long productId = 1L;
    Integer requestedQty = 10;

    Product product = new Product();
    product.setProductId(productId);
    product.setStock(50);
    product.setReservedStock(20);
    product.setAvailableStock(30);

    when(productGateway.findProductById(productId))
            .thenReturn(Mono.just(product));

    when(productGateway.updateProduct(any(Product.class)))
            .thenReturn(Mono.empty());

    StepVerifier.create(useCase.execute(productId, requestedQty))
            .expectNextMatches(updated ->
                    updated.getStock() == 40 &&
                            updated.getReservedStock() == 10 &&
                            updated.getAvailableStock() == 30  // 40 - 10
            )
            .verifyComplete();

    verify(productGateway).findProductById(productId);
    verify(productGateway).updateProduct(eq(product)); // matcher que significa, debe ser el mismo objeto, para asegurarnos de que actualizó el producto
  }

}
