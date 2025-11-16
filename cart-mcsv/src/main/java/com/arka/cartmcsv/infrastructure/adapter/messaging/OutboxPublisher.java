package com.arka.cartmcsv.infrastructure.adapter.messaging;

import com.arka.cartmcsv.application.event.EventPublisher;
import com.arka.cartmcsv.domain.event.CartConfirmEvent;
import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.infrastructure.adapter.entities.event.OutboxEventEntity;
import com.arka.cartmcsv.infrastructure.adapter.repositories.event.OutboxEntityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Transactional
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
        CartConfirmEvent cartEvent  = objectMapper.readValue(event.getPayload(), CartConfirmEvent.class);

        switch (event.getType()){
          case "CartConfirmedEvent":
            eventPublisher.onCartConfirmed(cartEvent);
            break;
          case "CartCancelledEvent":
            eventPublisher.onCartCancelled(cartEvent);
            break;
        }

        outboxEntityRepository.markAsPublished(event.getId());

      }catch (Exception e){
        log.error("Failed to publish event {}: {}", event.getId(), e.getMessage(), e);
      }

    }
  }
}
