package com.arka.cartmcsv.infrastructure.adapters.mappers;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartItem;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.infrastructure.adapters.entities.CartEntity;
import com.arka.cartmcsv.infrastructure.controllers.dtos.CartDto;
import com.arka.cartmcsv.infrastructure.controllers.dtos.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

  public static Cart toDomain(CartEntity entity) {
    if (entity == null) return null;

    Cart cart = new Cart();
    cart.setShoppingCartId(entity.getShoppingCartId());
    cart.setUserId(entity.getUserId());
    cart.setStatus(entity.getStatus()); // assuming stored as String
    cart.setTotalCost(entity.getTotalCost());

    if (entity.getCartItems() != null) {
      cart.setCartItems(
              entity.getCartItems().stream()
                      .map(CartItemMapper::toDomain)
                      .collect(Collectors.toList())
      );
    }
    return cart;
  }

  public static CartEntity toEntity(Cart domain) {
    if (domain == null) return null;

    CartEntity entity = new CartEntity();

    entity.setShoppingCartId(domain.getShoppingCartId());
    entity.setUserId(domain.getUserId());
    entity.setStatus(domain.getStatus()); // save enum as String
    entity.setTotalCost(domain.getTotalCost());

    if (domain.getCartItems() != null) {
      entity.setCartItems(
              domain.getCartItems().stream()
                      .map(CartItemMapper::toEntity)
                      .collect(Collectors.toList())
      );
    }
    return entity;
  }
}
