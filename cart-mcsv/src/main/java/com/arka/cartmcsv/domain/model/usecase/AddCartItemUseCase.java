package com.arka.cartmcsv.domain.model.usecase;

import com.arka.cartmcsv.domain.model.CartItem;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;
import com.arka.cartmcsv.domain.model.Cart;
import lombok.RequiredArgsConstructor;


import java.math.BigDecimal;
import java.util.ArrayList;

@RequiredArgsConstructor
public class AddCartItemUseCase {
  private final CartGateway cartGateway;

  public Cart addCartItem(CartItem item, Long userId) {
    // find existing cart by user + pending status
    Cart cart = cartGateway.findByUserIdAndStatus(userId, CartStatus.PENDING)
            .orElseGet(() -> {
              Cart newCart = new Cart();
              newCart.setUserId(userId);
              newCart.setStatus(CartStatus.PENDING);
              newCart.setTotalCost(BigDecimal.ZERO);
              newCart.setCartItems(new ArrayList<>());
              return newCart;
            });

    // attach new item
    item.setCart(cart);
    cart.getCartItems().add(item);

    // recalculate total cost
    BigDecimal totalCost = cart.getCartItems()
            .stream()
            .map(ci -> ci.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    cart.setTotalCost(totalCost);

    // save via gateway
    return cartGateway.save(cart);
  }



}
