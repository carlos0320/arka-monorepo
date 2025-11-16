package com.arka.cartmcsv.infrastructure.adapter.mappers;

import com.arka.cartmcsv.domain.model.CartItem;
import com.arka.cartmcsv.infrastructure.adapter.entities.CartItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CartItemEntityMapper {

  public static CartItemEntity toEntity(CartItem cartItem) {
    if (cartItem == null) return null;

    CartItemEntity cartItemEntity = new CartItemEntity();

    cartItemEntity.setUnitPrice(cartItem.getUnitPrice());

    cartItemEntity.setCartItemId(cartItem.getCartItemId());
    cartItemEntity.setQuantity(cartItem.getQuantity());
    cartItemEntity.setProductId(cartItem.getProductId());
    cartItemEntity.setTotalCost(cartItem.getTotalCost());
    cartItemEntity.setCreatedAt(cartItem.getCreatedAt());

    cartItemEntity.setProductImage(cartItem.getProductImage());
    cartItemEntity.setProductName(cartItem.getProductName());

    return cartItemEntity;
  }

  public static CartItem toDomain(CartItemEntity cartItemEntity) {
    if (cartItemEntity == null) return null;

    CartItem cartItem = new CartItem();

    cartItem.setCartItemId(cartItemEntity.getCartItemId());

    cartItem.setUnitPrice(cartItemEntity.getUnitPrice());

    cartItem.setQuantity(cartItemEntity.getQuantity());
    cartItem.setProductId(cartItemEntity.getProductId());
    cartItem.setTotalCost(cartItemEntity.getTotalCost());
    cartItem.setCreatedAt(cartItemEntity.getCreatedAt());
    cartItem.setUpdatedAt(cartItemEntity.getUpdatedAt());

    cartItem.setProductImage(cartItemEntity.getProductImage());
    cartItem.setProductName(cartItemEntity.getProductName());
    return cartItem;
  }
}

