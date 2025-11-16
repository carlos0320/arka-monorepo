package com.arka.ordermcsv.domain.event.gateway;

import com.arka.ordermcsv.domain.event.OutboxEvent;

import java.util.List;

public interface OutboxEventGateway {
  OutboxEvent save(OutboxEvent event);
  List<OutboxEvent> findUnpublished();
  void markAsPublished(OutboxEvent event);
}
