package com.arka.cartmcsv.domain.usecase.cart;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;

import java.util.List;

public class GetAbandonedCartsUseCase {
  private final CartGateway cartGateway;

  public GetAbandonedCartsUseCase(CartGateway cartGateway) {
    this.cartGateway = cartGateway;
  }

  public List<Cart> execute(){
    return cartGateway.getAbandonedCarts();
  }
}
