package com.arka.cartmcsv.infrastructure.adapters.repositories;

import com.arka.cartmcsv.infrastructure.adapters.entities.CartEntity;
import com.arka.cartmcsv.domain.model.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
  Optional<CartEntity> findByUserIdAndStatus(Long userId, CartStatus status);
}
