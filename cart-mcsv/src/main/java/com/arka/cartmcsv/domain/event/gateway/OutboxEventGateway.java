package com.arka.cartmcsv.domain.event.gateway;

import com.arka.cartmcsv.domain.event.OutboxEvent;

import java.util.List;

public interface OutboxEventGateway {
  OutboxEvent save(OutboxEvent event);
  List<OutboxEvent> findUnpublished();
  void markAsPublished(OutboxEvent event);
}
