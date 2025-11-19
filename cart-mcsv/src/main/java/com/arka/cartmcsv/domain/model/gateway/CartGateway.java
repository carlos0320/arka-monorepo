package com.arka.cartmcsv.domain.model.gateway;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartStatus;

import java.util.List;
import java.util.Optional;

public interface CartGateway {
  Cart save(Cart cart);
  void updateCart(Cart cart);
  Optional<Cart> findCartByUserIdAndStatuses(Long userId, List<CartStatus> statuses);
  void removeCart(Long cartId);
  List<Cart> getAbandonedCarts();

}
