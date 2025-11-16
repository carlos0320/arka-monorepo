package com.arka.cartmcsv.domain.usecase.cart;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;

public class GetUserCartUseCase {
  private final CartGateway cartGateway;

  public GetUserCartUseCase(CartGateway cartGateway) {
    this.cartGateway = cartGateway;
  }

  public Cart execute(Long userId){
    if (userId == null || userId <= 0) {
      throw new IllegalArgumentException("User id can't be null and must be valid");
    }

    Cart cart = cartGateway.findCartByUserIdAndStatus(userId, CartStatus.PENDING)
            .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

    return cart;
  }
}
