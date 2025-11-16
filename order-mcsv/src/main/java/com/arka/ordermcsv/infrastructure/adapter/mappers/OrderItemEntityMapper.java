package com.arka.ordermcsv.infrastructure.adapter.mappers;

import com.arka.ordermcsv.domain.model.OrderItem;
import com.arka.ordermcsv.infrastructure.adapter.entities.OrderItemEntity;

public class OrderItemEntityMapper {

  public static OrderItemEntity toEntity(OrderItem orderItem){
    OrderItemEntity orderItemEntity = new OrderItemEntity();
    orderItemEntity.setOrderItemId(orderItem.getOrderItemId());
    orderItemEntity.setQuantity(orderItem.getQuantity());
    orderItemEntity.setProductId(orderItem.getProductId());
    orderItemEntity.setProductName(orderItem.getProductName());
    orderItemEntity.setUnitPrice(orderItem.getUnitPrice());
    orderItemEntity.setTotalPrice(orderItem.getTotalPrice());

    return orderItemEntity;
  }

  public static  OrderItem toDomain(OrderItemEntity orderItemEntity){
    OrderItem orderItem = new OrderItem();

    orderItem.setOrderItemId(orderItemEntity.getOrderItemId());
    orderItem.setQuantity(orderItemEntity.getQuantity());
    orderItem.setTotalPrice(orderItemEntity.getTotalPrice());
    orderItem.setProductId(orderItemEntity.getProductId());
    orderItem.setUnitPrice(orderItemEntity.getUnitPrice());
    orderItem.setProductName(orderItemEntity.getProductName());

    return orderItem;
  }

}
