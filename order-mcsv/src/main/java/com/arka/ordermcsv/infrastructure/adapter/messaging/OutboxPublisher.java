package com.arka.ordermcsv.infrastructure.adapter.messaging;

import com.arka.ordermcsv.application.event.EventPublisher;
import com.arka.ordermcsv.domain.event.InventoryEvent;
import com.arka.ordermcsv.domain.event.NotificationEvent;
import com.arka.ordermcsv.domain.event.OrderEvent;
import com.arka.ordermcsv.infrastructure.adapter.entities.event.OutboxEventEntity;
import com.arka.ordermcsv.infrastructure.adapter.repositories.event.OutboxEntityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OutboxPublisher {
  private final OutboxEntityRepository outboxEntityRepository;
  private final EventPublisher eventPublisher;
  private final ObjectMapper objectMapper;

  public OutboxPublisher(OutboxEntityRepository outboxEntityRepository, EventPublisher eventPublisher, ObjectMapper objectMapper) {
    this.outboxEntityRepository = outboxEntityRepository;
    this.eventPublisher = eventPublisher;
    this.objectMapper = objectMapper;
  }

  @Transactional
  public void publishPendingEvents(){
    List<OutboxEventEntity> pendingEvents = outboxEntityRepository.findByPublishedFalse();

    if (pendingEvents.isEmpty()){
      return;
    }

    for(OutboxEventEntity event : pendingEvents){
      try {
        log.info("Publishing outbox event: {}", event.getId());
        switch (event.getType()){
          case "OrderCreatedEvent":
            NotificationEvent orderCreatedEvent = objectMapper.readValue(event.getPayload(), NotificationEvent.class);
            System.out.println("orderCreatedEvent!!!!  " + orderCreatedEvent);
            eventPublisher.orderCreatedNotificationEvent(orderCreatedEvent);
            break;
          case "OrderConfirmedInventoryEvent":
            InventoryEvent inventoryEvent = objectMapper.readValue(event.getPayload(), InventoryEvent.class);
            eventPublisher.orderConfirmedInventoryEvent(inventoryEvent);
            break;
          case "OrderConfirmedNotificationEvent":
            NotificationEvent orderConfirmed = objectMapper.readValue(event.getPayload(), NotificationEvent.class);
            eventPublisher.orderConfirmedNotificationEvent(orderConfirmed);
            break;
          case "OrderShippedNotificationEvent":
            NotificationEvent orderShipped = objectMapper.readValue(event.getPayload(), NotificationEvent.class);
            eventPublisher.orderShippedNotificationEvent(orderShipped);
            break;
        }
        outboxEntityRepository.markAsPublished(event.getId());

      }catch (Exception e){
        log.error("Failed to publish event {}: {}", event.getId(), e.getMessage(), e);
      }

    }
  }
}
