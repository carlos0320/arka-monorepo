package com.arka.ordermcsv.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutboxEvent {
  private UUID id;

  private String aggregateType;
  private Long aggregateId;
  private String type;

  private String payload;
  private LocalDateTime createdAt;
  private boolean published;

  public static OutboxEvent from(String aggregateType, Long aggregateId, String type, String payload){
    return new OutboxEvent(
            null,
            aggregateType,
            aggregateId,
            type,
            payload,
            LocalDateTime.now(),
            false
    );
  }
}
