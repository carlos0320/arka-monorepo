package com.arka.ordermcsv.application;

import com.arka.ordermcsv.domain.event.gateway.OutboxEventGateway;
import com.arka.ordermcsv.domain.model.gateway.OrderGateway;
import com.arka.ordermcsv.domain.usecases.CreateOrderUseCase;
import com.arka.ordermcsv.domain.usecases.ConfirmOrderUseCase;
import com.arka.ordermcsv.domain.usecases.ShipOrderUseCase;
import com.arka.ordermcsv.infrastructure.adapter.messaging.RabbitMQEventListener;
import com.arka.ordermcsv.infrastructure.adapter.repositories.OrderEntityRepository;
import com.arka.ordermcsv.infrastructure.adapter.repositories.OrderGatewayImpl;
import com.arka.ordermcsv.infrastructure.adapter.repositories.event.OutboxEntityRepository;
import com.arka.ordermcsv.infrastructure.adapter.repositories.event.OutboxEventGatewayImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class OrderConfig {

  @Bean
  public OrderGateway orderGateway(OrderEntityRepository orderEntityRepository) {
    return new OrderGatewayImpl(orderEntityRepository);
  }

  @Bean
  public OutboxEventGateway outboxEventGateway(OutboxEntityRepository outboxEntityRepository) {
    return new OutboxEventGatewayImpl(outboxEntityRepository);
  }


  @Bean
  public CreateOrderUseCase createOrderUseCase(OrderGateway orderGateway,ObjectMapper objectMapper, OutboxEventGateway outboxEventGateway) {
    return new CreateOrderUseCase(orderGateway,objectMapper,outboxEventGateway);
  }

  @Bean
  public ConfirmOrderUseCase orderConfirmedUseCase(OrderGateway orderGateway, OutboxEventGateway outboxEventGateway, ObjectMapper objectMapper) {
    return new ConfirmOrderUseCase(orderGateway,outboxEventGateway,objectMapper);
  }

  @Bean
  public ShipOrderUseCase shipOrderUseCase(OrderGateway orderGateway, OutboxEventGateway outboxEventGateway, ObjectMapper objectMapper) {
    return new ShipOrderUseCase(orderGateway,outboxEventGateway,objectMapper);
  }

  @Bean
  public RabbitMQEventListener rabbitMQEventListener(CreateOrderUseCase createOrderUseCase) {
    return new RabbitMQEventListener(createOrderUseCase);
  }



}
