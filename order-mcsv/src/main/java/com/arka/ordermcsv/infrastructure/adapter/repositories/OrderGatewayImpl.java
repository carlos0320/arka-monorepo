package com.arka.ordermcsv.infrastructure.adapter.repositories;

import com.arka.ordermcsv.domain.model.Order;
import com.arka.ordermcsv.domain.model.gateway.OrderGateway;
import com.arka.ordermcsv.infrastructure.adapter.entities.OrderEntity;
import com.arka.ordermcsv.infrastructure.adapter.mappers.OrderEntityMapper;

import java.util.Optional;

public class OrderGatewayImpl implements OrderGateway {

  private final OrderEntityRepository orderEntityRepository;

  public OrderGatewayImpl(OrderEntityRepository orderEntityRepository) {
    this.orderEntityRepository = orderEntityRepository;
  }

  @Override
  public Order saveOrder(Order order) {
    OrderEntity orderEntity = OrderEntityMapper.toEntity(order);
    OrderEntity orderEntitySaved = orderEntityRepository.save(orderEntity);
    return OrderEntityMapper.toDomain(orderEntitySaved);
  }

  @Override
  public Optional<Order> getOrderByOrderIdAndStatus(Long orderId, String status) {
    Optional<Order> orderEntity = orderEntityRepository.findOrderEntitiesByOrderIdAndStatus(orderId, status)
            .map(OrderEntityMapper::toDomain);
    return orderEntity;
  }
}
