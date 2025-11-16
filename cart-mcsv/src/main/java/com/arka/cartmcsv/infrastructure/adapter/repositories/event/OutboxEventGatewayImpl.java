package com.arka.cartmcsv.infrastructure.adapter.repositories.event;

import com.arka.cartmcsv.domain.event.OutboxEvent;
import com.arka.cartmcsv.domain.event.gateway.OutboxEventGateway;
import com.arka.cartmcsv.infrastructure.adapter.entities.event.OutboxEventEntity;
import com.arka.cartmcsv.infrastructure.adapter.mappers.event.OutboxEventMapper;

import java.util.List;
import java.util.stream.Collectors;

public class OutboxEventGatewayImpl implements OutboxEventGateway {
  private final OutboxEntityRepository outboxEntityRepository;

  public OutboxEventGatewayImpl(OutboxEntityRepository outboxEntityRepository) {
    this.outboxEntityRepository = outboxEntityRepository;
  }

  @Override
  public OutboxEvent save(OutboxEvent event) {
    OutboxEventEntity outboxEventEntity = OutboxEventMapper.toEntity(event);
    OutboxEventEntity eventSaved = outboxEntityRepository.save(outboxEventEntity);
    return OutboxEventMapper.toDomain(eventSaved);
  }

  @Override
  public List<OutboxEvent> findUnpublished() {
    return outboxEntityRepository.findByPublishedFalse()
            .stream()
            .map(OutboxEventMapper::toDomain)
            .collect(Collectors.toList());

  }

  @Override
  public void markAsPublished(OutboxEvent event) {
    event.setPublished(true);
    OutboxEventEntity outboxEventEntity = OutboxEventMapper.toEntity(event);
    OutboxEventEntity eventSaved = outboxEntityRepository.save(outboxEventEntity);
  }
}
