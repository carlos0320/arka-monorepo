package com.arka.cartmcsv.domain.model.usecase;


import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetCartItemsUseCase {

  private final CartGateway cartGateway;

  public Cart getCartItems(Long userId) {
    Cart cart = cartGateway.findByUserIdAndStatus(userId, CartStatus.PENDING)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
    return cart;
  }
}
