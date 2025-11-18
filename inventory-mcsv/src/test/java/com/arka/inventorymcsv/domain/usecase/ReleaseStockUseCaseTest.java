package com.arka.inventorymcsv.domain.usecase;


import com.arka.inventorymcsv.domain.exceptions.BusinessException;
import com.arka.inventorymcsv.domain.exceptions.NotFoundException;
import com.arka.inventorymcsv.domain.model.Product;
import com.arka.inventorymcsv.domain.model.gateway.ProductGateway;
import com.arka.inventorymcsv.domain.usecase.product.ReleaseStockUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReleaseStockUseCaseTest {

  @Mock
  private ProductGateway productGateway;

  private ReleaseStockUseCase useCase;

  @BeforeEach
  void setUp() {
    useCase = new ReleaseStockUseCase(productGateway);
  }

  @Test
  void shouldReleaseStockSuccessfully() {
    // Arrange
    Product product = new Product();
    product.setProductId(1L);
    product.setStock(20);
    product.setReservedStock(10);
    product.setAvailableStock(10);

    when(productGateway.findProductById(1L)).thenReturn(Mono.just(product));
    when(productGateway.updateProduct(any(Product.class)))
            .thenReturn(Mono.empty());   // <-// usamos any para garantizar de que devuelva Mono.empty

    StepVerifier.create(useCase.execute(1L, 5))
            .assertNext(updated -> {
              // reserved: 10 - 5 = 5 -> el usuario elimina productos del carrito de compra
              // available = stock - reserved = 20 - 5 = 15
              assert updated.getReservedStock() == 5;
              assert updated.getAvailableStock() == 15;
            })
            .verifyComplete();

    verify(productGateway).findProductById(1L);
    verify(productGateway).updateProduct(any(Product.class));
  }

  @Test
  void shouldThrowNotFoundExceptionWhenProductDoesNotExist() {
    when(productGateway.findProductById(99L)).thenReturn(Mono.empty()); // producto no existe

    StepVerifier.create(useCase.execute(99L, 5))
            .expectError(NotFoundException.class)
            .verify();

    verify(productGateway).findProductById(99L);
  }

  @Test
  void shouldThrowBusinessExceptionWhenQuantityExceedsReservedStock() {
    // cuando queremos liberar stock reservado más de lo que se tiene reservado
    Product product = new Product();
    product.setProductId(1L);
    product.setStock(10);
    product.setReservedStock(3);
    product.setAvailableStock(7);

    when(productGateway.findProductById(1L)).thenReturn(Mono.just(product));

    // Act & Assert
    StepVerifier.create(useCase.execute(1L, 5))
            .expectError(BusinessException.class)
            .verify();

    verify(productGateway).findProductById(1L);
  }
}
