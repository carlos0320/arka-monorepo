package com.arka.cartmcsv.domain.model.usecase;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartItem;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteCartItemUseCase {
  private final CartGateway cartGateway;

  public void deleteCartItem(CartItem item, Long userId) {
    Cart cart = cartGateway.findByUserIdAndStatus(userId, CartStatus.PENDING)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

    boolean removed = cart.getCartItems()
            .removeIf(i -> i.getShoppingCartItemId().equals(item.getShoppingCartItemId()));

    if (!removed) {
      throw new RuntimeException("Cart item not found in cart");
    }

    if (cart.getCartItems().isEmpty()) {
      cart.setStatus(CartStatus.CANCELLED);
    }

    cartGateway.save(cart);
  }
}
