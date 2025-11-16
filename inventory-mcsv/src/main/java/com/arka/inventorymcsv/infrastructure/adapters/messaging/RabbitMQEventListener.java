package com.arka.inventorymcsv.infrastructure.adapters.messaging;

import com.arka.inventorymcsv.application.event.EventConsumer;
import com.arka.inventorymcsv.domain.usecase.product.ReduceStockUseCase;
import com.arka.inventorymcsv.domain.usecase.product.ReleaseStockUseCase;
import com.arka.inventorymcsv.domain.usecase.product.ReserveStockUseCase;
import com.arka.inventorymcsv.infrastructure.adapters.messaging.dto.CartEventDto;
import com.arka.inventorymcsv.infrastructure.adapters.messaging.dto.OrderEventDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RabbitMQEventListener implements EventConsumer {

  private final ReleaseStockUseCase releaseStockUseCase;
  private final ReduceStockUseCase reduceStockUseCase;

  public RabbitMQEventListener(
          ReleaseStockUseCase releaseStockUseCase,
          ReduceStockUseCase reduceStockUseCase
  ) {
    this.releaseStockUseCase = releaseStockUseCase;
    this.reduceStockUseCase = reduceStockUseCase;
  }

  @Override
  @RabbitListener(queues = "${rabbitmq.order.queues.confirmed.inventory}")
  public Mono<Void> onOrderConfirmed(OrderEventDto event) {
    return Mono.defer(() -> {
      System.out.println("Processing order confirmed (inventory): " + event.getOrderId());
      return Flux.fromIterable(event.getItems())
              .flatMap(item -> reduceStockUseCase.execute(item.getProductId(), item.getQuantity()), 4)
              .then()
              .doOnSuccess(v -> System.out.println("Finished reserving stock for order " + event.getOrderId()))
              .onErrorResume(e -> {
                System.err.println("Error reserving stock for order "
                        + event.getOrderId() + ": " + e.getMessage());
                return Mono.empty();
              });
    });
  }

  @Override
  @RabbitListener(queues = "${rabbitmq.order.queues.cancelled}")
  public Mono<Void> onCartCancelled(CartEventDto event) {
    return Mono.defer(() -> {
      System.out.println("Processing order cancelled: " + event.getCartId());
      return Flux.fromIterable(event.getItems())
              .flatMap(item -> releaseStockUseCase.execute(item.getProductId(), item.getQuantity()), 4)
              .then()
              .doOnSuccess(v -> System.out.println("Released stock for order " + event.getCartId()))
              .onErrorResume(e -> {
                System.err.println("Error releasing stock for order "
                        + event.getCartId() + ": " + e.getMessage());
                return Mono.empty();
              });
    });
  }
}
