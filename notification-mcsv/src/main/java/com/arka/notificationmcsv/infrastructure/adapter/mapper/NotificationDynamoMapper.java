package com.arka.notificationmcsv.infrastructure.adapter.mapper;

import com.arka.notificationmcsv.domain.model.Notification;
import com.arka.notificationmcsv.domain.model.NotificationStatus;
import com.arka.notificationmcsv.domain.model.NotificationType;
import com.arka.notificationmcsv.infrastructure.adapter.entities.NotificationDynamoEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class NotificationDynamoMapper {
  private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
  public static NotificationDynamoEntity toDynamoEntity(Notification notification) {
    if (notification == null) return null;

    String id = notification.getId();
    if (id == null || id.isBlank()) {
      // En Mongo se generaba el id automáticamente, en Dynamo necesitamos generarlo
      id = "notif-" + UUID.randomUUID();
    }

    // Convertimos LocalDateTime -> String (UTC)
    String createdAtStr = null;
    if (notification.getCreatedAt() != null) {
      createdAtStr = notification.getCreatedAt()
              .atOffset(ZoneOffset.UTC)
              .format(ISO_FORMATTER);
    }

    return NotificationDynamoEntity.builder()
            .id(id)
            .userEmail(notification.getUserEmail())
            .userId(notification.getUserId())
            .createdAt(createdAtStr)
            .type(notification.getType() != null ? notification.getType().toString() : null)
            .emailMessage(notification.getEmailMessage())
            .status(notification.getStatus().getValue())
            .errorLogs(notification.getErrorLogs())
            .build();
  }

  // DynamoDB entity -> Domain
  public static Notification toDomain(NotificationDynamoEntity entity) {
    if (entity == null) return null;

    LocalDateTime createdAt = null;
    if (entity.getCreatedAt() != null) {
      createdAt = LocalDateTime.parse(entity.getCreatedAt(), ISO_FORMATTER);
    }

    Notification notification = new Notification();
    notification.setId(entity.getId());
    notification.setUserEmail(entity.getUserEmail());
    notification.setUserId(entity.getUserId());
    notification.setCreatedAt(createdAt);
    // Si tu NotificationType es un enum:
    // notification.setType(NotificationType.valueOf(entity.getType()));
    notification.setType(NotificationType.valueOf(entity.getType().toUpperCase()));
    notification.setEmailMessage(entity.getEmailMessage());
    notification.setStatus(NotificationStatus.valueOf(entity.getStatus().toUpperCase()));
    notification.setErrorLogs(entity.getErrorLogs());

    return notification;
  }
}
