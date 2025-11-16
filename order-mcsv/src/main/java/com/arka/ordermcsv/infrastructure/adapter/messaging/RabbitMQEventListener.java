package com.arka.ordermcsv.infrastructure.adapter.messaging;

import com.arka.ordermcsv.application.event.EventCart;
import com.arka.ordermcsv.domain.model.OrderItem;
import com.arka.ordermcsv.domain.usecases.CreateOrderUseCase;
import com.arka.ordermcsv.infrastructure.adapter.mappers.OrderEventMapper;
import com.arka.ordermcsv.infrastructure.adapter.messaging.dto.OrderEventDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class RabbitMQEventListener implements EventCart {

  private final CreateOrderUseCase createOrderUseCase;

  public RabbitMQEventListener(CreateOrderUseCase createOrderUseCase) {
    this.createOrderUseCase = createOrderUseCase;
  }

  @Override
  @Transactional
  @RabbitListener(queues = "${rabbitmq.cart.queues.confirmed}")
  public void onOrderCreated(OrderEventDto orderEvent) {
    System.out.println("🟢 Received order event!!!!!!!!!!!!!!: {}" + orderEvent);

    try{
      List<OrderItem> orderItems = OrderEventMapper.toDomainOrderItems(orderEvent);

      createOrderUseCase.execute(
              orderEvent.getUserData().getUserId(),
              orderEvent.getUserData().getName(),
              orderEvent.getUserData().getEmail(),
              orderEvent.getUserData().getPhone(),
              orderEvent.getUserData().getAddress(),
              orderEvent.getUserData().getClient().getCustomerName(),
              orderEvent.getUserData().getClient().getClientId(),
              orderItems
      );
    }catch (Exception e){
      System.err.println("Failed to create order for user "
              + orderEvent.getUserData().getUserId() + ": " + e.getMessage());

    }

  }
}
