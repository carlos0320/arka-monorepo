package com.arka.cartmcsv.application.event;

public interface EventPublisher {
  void onCartCancelled(Object event);
  void onCartConfirmed(Object event);
}
