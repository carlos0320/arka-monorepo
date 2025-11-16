package com.arka.ordermcsv.infrastructure.adapter.mappers.event;


import com.arka.ordermcsv.domain.event.OutboxEvent;
import com.arka.ordermcsv.infrastructure.adapter.entities.event.OutboxEventEntity;

public class OutboxEventMapper {

  public static OutboxEvent toDomain(OutboxEventEntity entity) {
    OutboxEvent outboxEvent = new OutboxEvent();
    outboxEvent.setId(entity.getId());
    outboxEvent.setPayload(entity.getPayload());
    outboxEvent.setAggregateId(entity.getAggregateId());
    outboxEvent.setType(entity.getType());
    outboxEvent.setCreatedAt(entity.getCreatedAt());
    outboxEvent.setAggregateType(entity.getAggregateType());
    return outboxEvent;
  }

  public static OutboxEventEntity toEntity(OutboxEvent outboxEvent) {
    OutboxEventEntity outboxEventEntity = new OutboxEventEntity();
    outboxEventEntity.setPayload(outboxEvent.getPayload());
    outboxEventEntity.setAggregateId(outboxEvent.getAggregateId());
    outboxEventEntity.setType(outboxEvent.getType());
    outboxEventEntity.setCreatedAt(outboxEvent.getCreatedAt());
    outboxEventEntity.setAggregateType(outboxEvent.getAggregateType());
    return outboxEventEntity;
  }
}