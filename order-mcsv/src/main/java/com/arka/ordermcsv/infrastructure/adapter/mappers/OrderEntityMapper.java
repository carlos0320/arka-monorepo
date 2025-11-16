package com.arka.ordermcsv.infrastructure.adapter.mappers;

import com.arka.ordermcsv.domain.model.Order;
import com.arka.ordermcsv.domain.model.OrderItem;
import com.arka.ordermcsv.domain.model.OrderStatus;
import com.arka.ordermcsv.infrastructure.adapter.entities.OrderEntity;
import com.arka.ordermcsv.infrastructure.adapter.entities.OrderItemEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderEntityMapper {

  public static OrderEntity toEntity(Order order){
    OrderEntity orderEntity = new OrderEntity();

    orderEntity.setOrderId(order.getOrderId());
    orderEntity.setCancelledAt(order.getCancelledAt());
    orderEntity.setConfirmedAt(order.getConfirmedAt());
    orderEntity.setStatus(order.getStatus().getValue());
    orderEntity.setCreatedAt(order.getCreatedAt());
    orderEntity.setUserName(order.getUserName());
    orderEntity.setContactEmail(order.getContactEmail());
    orderEntity.setContactPhone(order.getContactPhone());
    orderEntity.setDeliveredAt(order.getDeliveredAt());
    orderEntity.setUserId(order.getUserId());
    orderEntity.setShippedAt(order.getShippedAt());
    orderEntity.setTotalPrice(order.getTotalPrice());
    orderEntity.setShippingAddress(order.getShippingAddress());
    orderEntity.setClientId(order.getClientId());
    orderEntity.setCustomerName(order.getCustomerName());

    if ((order.getItems() != null && !order.getItems().isEmpty())){
      List<OrderItemEntity> entityItems = order.getItems()
              .stream()
              .map(OrderItemEntityMapper::toEntity)
              .collect(Collectors.toList());

      entityItems.forEach(item -> item.setOrder(orderEntity));
      orderEntity.setOrderItems(entityItems);
    }else{
      orderEntity.setOrderItems(new ArrayList<>());
    }

    return orderEntity;
  }

  public static Order toDomain(OrderEntity orderEntity) {
    Order order = new Order();

    order.setOrderId(orderEntity.getOrderId());
    order.setCreatedAt(orderEntity.getCreatedAt());
    order.setStatus(OrderStatus.valueOf(orderEntity.getStatus().toUpperCase()));
    order.setCancelledAt(orderEntity.getCancelledAt());
    order.setContactPhone(orderEntity.getContactPhone());
    order.setDeliveredAt(orderEntity.getDeliveredAt());
    order.setShippingAddress(orderEntity.getShippingAddress());
    order.setShippedAt(orderEntity.getShippedAt());
    order.setUserId(orderEntity.getUserId());
    order.setTotalPrice(orderEntity.getTotalPrice());
    order.setConfirmedAt(orderEntity.getConfirmedAt());

    order.setContactEmail(orderEntity.getContactEmail());
    order.setContactPhone(orderEntity.getContactPhone());
    order.setUserName(orderEntity.getUserName());

    order.setClientId(orderEntity.getClientId());
    order.setCustomerName(orderEntity.getCustomerName());

    if (orderEntity.getOrderItems() != null && !orderEntity.getOrderItems().isEmpty()) {
      List<OrderItem> orderItems = orderEntity.getOrderItems()
              .stream()
              .map(OrderItemEntityMapper::toDomain)
              .collect(Collectors.toList());

      orderItems.forEach(item -> item.setOrder(order));
      order.setItems(orderItems);
    }else{
      order.setItems(new ArrayList<>());
    }

    return order;
  }

}
