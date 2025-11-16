package com.arka.notificationmcsv.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailTemplate {
  private NotificationType type;
  private String subject;
  private String body;
}
