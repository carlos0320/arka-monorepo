package com.arka.ordermcsv.infrastructure.adapter.repositories;

import com.arka.ordermcsv.infrastructure.adapter.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {
  Optional<OrderEntity> findOrderEntitiesByOrderIdAndStatus(Long orderId, String status);
}
