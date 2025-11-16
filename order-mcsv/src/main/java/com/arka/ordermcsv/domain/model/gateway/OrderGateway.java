package com.arka.ordermcsv.domain.model.gateway;

import com.arka.ordermcsv.domain.model.Order;

import java.util.Optional;

public interface OrderGateway {
  Order saveOrder(Order order);
  Optional<Order> getOrderByOrderIdAndStatus(Long orderId, String status);
}
