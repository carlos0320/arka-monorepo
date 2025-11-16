package com.arka.cartmcsv.infrastructure.adapter.messaging.dto;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartEventDto {
  private final Long cartId;
  private final List<CartItemEvent> items;

  @Data
  @AllArgsConstructor
  public static class CartItemEvent{
    private final Long productId;
    private final Integer quantity;
  }

  public static CartEventDto fromCart(Cart cart){
    List<CartItemEvent> itemEvents = cart.getItems()
            .stream()
            .map(item -> new CartItemEvent(item.getProductId(), item.getQuantity()))
            .collect(Collectors.toList());
    return new CartEventDto(cart.getCartId(), itemEvents);
  }
}
