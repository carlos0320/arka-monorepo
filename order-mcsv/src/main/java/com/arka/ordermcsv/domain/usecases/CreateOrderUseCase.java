package com.arka.ordermcsv.domain.usecases;


import com.arka.ordermcsv.domain.event.OrderEvent;
import com.arka.ordermcsv.domain.event.OutboxEvent;
import com.arka.ordermcsv.domain.event.gateway.OutboxEventGateway;
import com.arka.ordermcsv.domain.exception.InvalidUserDataException;
import com.arka.ordermcsv.domain.exception.OrderItemsNotFoundException;
import com.arka.ordermcsv.domain.exception.UserIdInvalidException;
import com.arka.ordermcsv.domain.model.Order;
import com.arka.ordermcsv.domain.model.OrderItem;
import com.arka.ordermcsv.domain.model.gateway.OrderGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class CreateOrderUseCase {

  private final OrderGateway orderGateway;
  private final ObjectMapper objectMapper;
  private final OutboxEventGateway outboxEventGateway;

  public CreateOrderUseCase(OrderGateway orderGateway, ObjectMapper objectMapper, OutboxEventGateway outboxEventGateway) {
    this.orderGateway = orderGateway;
    this.objectMapper = objectMapper;
    this.outboxEventGateway = outboxEventGateway;
  }

  public void execute(
          Long userId,
          String userName,
          String contactEmail,
          String contactPhone,
          String shippingAddress,
          String customerName,
          Long clientId,
          List<OrderItem> orderItems){

    try{
      validateInputs(
              userId,
              userName,
              contactEmail,
              contactPhone,
              shippingAddress,
              customerName,
              clientId,
              orderItems);
      // create order
      Order order = Order.createOrder(
              userId,
              userName,
              contactEmail,
              contactPhone,
              shippingAddress,
              clientId,
              customerName
      );

      orderItems.forEach(orderItem -> order.addOrderItem(orderItem));
      order.calculateCost();

      Order orderSaved = orderGateway.saveOrder(order);

      // outbox pattern
      OrderEvent orderEvent = OrderEvent.fromOrder(orderSaved);
      String payload = objectMapper.writeValueAsString(orderEvent);

      OutboxEvent outboxEvent = OutboxEvent.from(
              "Order",
              orderSaved.getOrderId(),
              "OrderCreatedEvent",
              payload
      );

      outboxEventGateway.save(outboxEvent);
    }catch (Exception ex){
      throw new RuntimeException("Failed to save outbox event: " + ex.getMessage(), ex);
    }
  }

  private void validateInputs(Long userId,
                              String userName,
                              String contactEmail,
                              String contactPhone,
                              String shippingAddress,
                              String customerName,
                              Long clientId,
                              List<OrderItem> orderItems
  ){
    if (userId == null || userId <= 0){
      throw new UserIdInvalidException();
    }

    if (userName == null || userName.isEmpty()){
      throw new InvalidUserDataException("User name is required and must be valid.");
    }

    if (contactEmail == null || contactEmail.isEmpty()){
      throw new InvalidUserDataException("User email is required and must be valid.");
    }

    if (contactPhone == null || contactPhone.isEmpty()){
      throw new InvalidUserDataException("User phone is required and must be valid.");
    }

    if (shippingAddress == null || shippingAddress.isEmpty()){
      throw new InvalidUserDataException("User shipping address is required and must be valid.");
    }

    if (customerName == null || customerName.isEmpty()){
      throw new InvalidUserDataException("Client name  is required and must be valid.");
    }

    if (clientId == null || clientId <= 0){
      throw new InvalidUserDataException("Client id  is required and must be valid.");
    }

    if (orderItems.isEmpty()){
      throw new OrderItemsNotFoundException();
    }
  }

}
