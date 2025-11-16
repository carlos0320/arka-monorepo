package com.arka.notificationmcsv.domain.model.gateway;

import com.arka.notificationmcsv.domain.model.Notification;

public interface NotificationGateway {
  Notification save(Notification notification);
}
