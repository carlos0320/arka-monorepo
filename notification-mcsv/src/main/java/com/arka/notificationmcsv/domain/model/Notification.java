package com.arka.notificationmcsv.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
  private String id;
  private String userEmail;
  private Long userId;
  private LocalDateTime createdAt;
  private NotificationType type;
  private String emailMessage;
  private NotificationStatus status;
  private String errorLogs;
}
