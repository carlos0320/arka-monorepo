package com.arka.cartmcsv.domain.usecase.cart;

import com.arka.cartmcsv.domain.exceptions.CartNotFoundException;
import com.arka.cartmcsv.domain.exceptions.InvalidUserIdException;
import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;

import java.util.List;

public class GetUserCartUseCase {
  private final CartGateway cartGateway;

  public GetUserCartUseCase(CartGateway cartGateway) {
    this.cartGateway = cartGateway;
  }

  public Cart execute(Long userId){
    if (userId == null || userId <= 0) {
      throw new InvalidUserIdException();
    }

    Cart cart = cartGateway.findCartByUserIdAndStatuses(userId, List.of(CartStatus.PENDING, CartStatus.ABANDONED))
            .orElseThrow(() -> new CartNotFoundException());

    return cart;
  }
}
