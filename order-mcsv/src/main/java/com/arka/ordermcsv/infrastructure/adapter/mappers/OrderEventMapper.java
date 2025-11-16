package com.arka.ordermcsv.infrastructure.adapter.mappers;

import com.arka.ordermcsv.domain.model.OrderItem;
import com.arka.ordermcsv.infrastructure.adapter.messaging.dto.OrderEventDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class OrderEventMapper {
  public static List<OrderItem> toDomainOrderItems(OrderEventDto dto) {
    if (dto.getItems() == null) {
      return List.of();
    }

    return dto.getItems().
            stream()
            .map(item -> {
              BigDecimal totalPrice = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

              return new OrderItem(
                      null,
                      item.getProductId(),
                      item.getQuantity(),
                      item.getPrice(),
                      item.getProductName(),
                      totalPrice,
                      null
              );
            })
            .collect(Collectors.toList());
  }
}
