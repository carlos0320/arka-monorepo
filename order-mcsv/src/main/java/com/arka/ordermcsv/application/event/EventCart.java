package com.arka.ordermcsv.application.event;

import com.arka.ordermcsv.infrastructure.adapter.messaging.dto.OrderEventDto;

public interface EventCart {
  void onOrderCreated(OrderEventDto orderEvent);
}
