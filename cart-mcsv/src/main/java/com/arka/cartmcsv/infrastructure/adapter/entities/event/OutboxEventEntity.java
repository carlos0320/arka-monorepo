package com.arka.cartmcsv.infrastructure.adapter.entities.event;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(schema = "shopping", name = "outbox_events")
public class OutboxEventEntity {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;

  private String aggregateType;
  private Long aggregateId;
  private String type;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb", nullable = false)
  private String  payload;
  private LocalDateTime createdAt;
  private boolean published;
}
