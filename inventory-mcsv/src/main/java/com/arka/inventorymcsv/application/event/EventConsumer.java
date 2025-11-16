package com.arka.inventorymcsv.application.event;

import com.arka.inventorymcsv.infrastructure.adapters.messaging.dto.CartEventDto;
import com.arka.inventorymcsv.infrastructure.adapters.messaging.dto.OrderEventDto;
import reactor.core.publisher.Mono;

public interface EventConsumer {
  Mono<Void> onCartCancelled(CartEventDto event);
  Mono<Void> onOrderConfirmed(OrderEventDto event);
}
