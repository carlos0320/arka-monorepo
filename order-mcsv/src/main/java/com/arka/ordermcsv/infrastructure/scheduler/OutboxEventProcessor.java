package com.arka.ordermcsv.infrastructure.scheduler;

import com.arka.ordermcsv.infrastructure.adapter.messaging.OutboxPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class OutboxEventProcessor {
  private final OutboxPublisher outboxPublisher;

  @Scheduled(fixedDelay = 4000) // every 4 seconds
  public void processOutboxEvents() {
    log.info("Processing outbox events...");
    outboxPublisher.publishPendingEvents();
  }
}
