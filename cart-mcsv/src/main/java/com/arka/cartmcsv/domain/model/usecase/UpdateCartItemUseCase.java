package com.arka.cartmcsv.domain.model.usecase;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartItem;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class UpdateCartItemUseCase {
  private final CartGateway cartGateway;

  public Cart updateCartItem(CartItem cartItem, Long userId) {
    // 1. Fetch the cart from DB
    Cart cart = cartGateway.findByUserIdAndStatus(userId, CartStatus.PENDING)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

    // 2. Find the item in the cart
    CartItem cartItemStored = cart.getCartItems()
            .stream()
            .filter(i -> i.getShoppingCartItemId().equals(cartItem.getShoppingCartItemId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Cart item not found"));

    // 3. Update fields if provided
    if (cartItem.getPrice() != null) {
      cartItemStored.setPrice(cartItem.getPrice());
    }

    if (cartItem.getQuantity() != null) {
      cartItemStored.setQuantity(cartItem.getQuantity());
    }

    // 4. Recalculate total cost
    BigDecimal totalCost = cart.getCartItems()
            .stream()
            .map(CartItem::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    cart.setTotalCost(totalCost);

    // 5. Save and return updated cart
    cartGateway.save(cart);
    return cart;
  }
}
