package com.arka.ordermcsv.application.event;

import com.arka.ordermcsv.domain.event.InventoryEvent;
import com.arka.ordermcsv.domain.event.NotificationEvent;

public interface EventPublisher {
  void orderCreatedNotificationEvent(NotificationEvent notificationEvent);
  void orderConfirmedInventoryEvent(InventoryEvent orderEvent);
  void orderConfirmedNotificationEvent(NotificationEvent notificationEvent);
  void orderShippedNotificationEvent(NotificationEvent notificationEvent);
  void orderDeliveredNotificationEvent(NotificationEvent notificationEvent);
}
