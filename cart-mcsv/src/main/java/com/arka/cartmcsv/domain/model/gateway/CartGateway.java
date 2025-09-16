package com.arka.cartmcsv.domain.model.gateway;

import com.arka.cartmcsv.domain.model.CartItem;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.domain.model.Cart;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface CartGateway {
  Cart save(Cart cart);
  Optional<Cart> findByUserIdAndStatus(Long userId, CartStatus status);
}
