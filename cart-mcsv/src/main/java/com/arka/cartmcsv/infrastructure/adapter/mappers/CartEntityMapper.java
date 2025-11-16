package com.arka.cartmcsv.infrastructure.adapter.mappers;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartItem;
import com.arka.cartmcsv.domain.model.Client;
import com.arka.cartmcsv.domain.model.User;
import com.arka.cartmcsv.infrastructure.adapter.entities.CartEntity;
import com.arka.cartmcsv.infrastructure.adapter.entities.CartItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartEntityMapper {

  public static CartEntity toEntity(Cart cart) {
    if (cart == null) return null;

    CartEntity cartEntity = new CartEntity();

    cartEntity.setCartId(cart.getCartId());
    cartEntity.setStatus(cart.getStatus());
    cartEntity.setCreatedAt(cart.getCreatedAt());
    cartEntity.setUpdatedAt(cart.getUpdatedAt());

    if (cart.getUser() != null) {
      cartEntity.setUserId(cart.getUser().getUserId());
      cartEntity.setUserEmail(cart.getUser().getEmail());
      cartEntity.setUserName(cart.getUser().getName());
      cartEntity.setUserAddress(cart.getUser().getAddress());
      cartEntity.setUserPhone(cart.getUser().getPhone());
      cartEntity.setCustomerName(cart.getUser().getClient().getCustomerName());
      cartEntity.setClientId(cart.getUser().getClient().getClientId());
    }

    cartEntity.setTotalCost(cart.getTotalCost());

    if (cart.getItems() != null && !cart.getItems().isEmpty()) {
      List<CartItemEntity> cartItemEntities = cart.getItems().stream()
              .map(CartItemEntityMapper::toEntity)
              .collect(Collectors.toList());

      cartItemEntities.forEach(item -> item.setCart(cartEntity));
      cartEntity.setItems(cartItemEntities);
    } else {
      cartEntity.setItems(new ArrayList<>());
    }

    return cartEntity;
  }

  public static Cart toDomain(CartEntity cartEntity) {
    if (cartEntity == null) return null;

    Cart cart = new Cart();

    cart.setCartId(cartEntity.getCartId());
    cart.setStatus(cartEntity.getStatus());
    cart.setCreatedAt(cartEntity.getCreatedAt());
    cart.setUpdatedAt(cartEntity.getUpdatedAt());

    User user = new User();
    user.setUserId(cartEntity.getUserId());
    user.setName(cartEntity.getUserName());
    user.setEmail(cartEntity.getUserEmail());
    user.setPhone(cartEntity.getUserPhone());
    user.setAddress(cartEntity.getUserAddress());

    Client userClient = new Client();
    userClient.setCustomerName(cartEntity.getCustomerName());
    userClient.setClientId(cartEntity.getClientId());

    user.setClient(userClient);

    cart.setUser(user);
    cart.setTotalCost(cartEntity.getTotalCost());

    if (cartEntity.getItems() != null && !cartEntity.getItems().isEmpty()) {
      List<CartItem> cartItems = cartEntity.getItems().stream()
              .map(CartItemEntityMapper::toDomain)
              .collect(Collectors.toList());
      cartItems.forEach(item -> item.setCart(cart));
      cart.setItems(cartItems);
    } else {
      cart.setItems(new ArrayList<>());
    }

    return cart;
  }
}
