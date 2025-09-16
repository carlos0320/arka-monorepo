package com.arka.cartmcsv.infrastructure.adapters.repositories.impl;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;
import com.arka.cartmcsv.infrastructure.adapters.repositories.CartRepository;
import com.arka.cartmcsv.infrastructure.adapters.mappers.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // or @Component
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartGateway {

  private final CartRepository cartRepository;

  @Override
  public Cart save(Cart cart) {
    return CartMapper.toDomain(
            cartRepository.save(CartMapper.toEntity(cart))
    );
  }

  @Override
  public Optional<Cart> findByUserIdAndStatus(Long userId, CartStatus status) {
    return cartRepository.findByUserIdAndStatus(userId, status)
            .map(CartMapper::toDomain);
  }
}