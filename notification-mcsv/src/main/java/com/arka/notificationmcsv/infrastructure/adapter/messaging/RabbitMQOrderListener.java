package com.arka.notificationmcsv.infrastructure.adapter.messaging;


import com.arka.notificationmcsv.application.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import com.arka.notificationmcsv.infrastructure.messaging.orderDto.OrderEventDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQOrderListener {
  private final OrderEvent orderEvent;

  @RabbitListener(queues = "${rabbitmq.order.queues.created}")
  public void onOrderCreated(OrderEventDto orderEventDto) {
    System.out.println("orderEventDto!!!!! = " + orderEventDto);
    orderEvent.onOrderCreated(orderEventDto);
  }

  @RabbitListener(queues = "${rabbitmq.order.queues.confirmed.notification}")
  public void onOrderConfirmed(OrderEventDto dto) {
    orderEvent.onOrderConfirmed(dto);
  }

  @RabbitListener(queues = "${rabbitmq.order.queues.shipped.notification}")
  public void onOrderShipped(OrderEventDto dto) {
    orderEvent.onOrderShipped(dto);
  }

  @RabbitListener(queues = "${rabbitmq.order.queues.delivered.notification}")
  public void onOrderDelivered(OrderEventDto dto) {
    orderEvent.onOrderDelivered(dto);
  }
}
