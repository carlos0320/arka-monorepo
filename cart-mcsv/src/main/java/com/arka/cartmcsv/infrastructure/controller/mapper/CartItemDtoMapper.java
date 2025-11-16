package com.arka.cartmcsv.infrastructure.controller.mapper;

import com.arka.cartmcsv.domain.model.CartItem;
import com.arka.cartmcsv.infrastructure.controller.dto.CartItemDto;

public class CartItemDtoMapper {
  public static CartItemDto toDto(CartItem cartItem){
    CartItemDto cartItemDto = new CartItemDto();
    cartItemDto.setCartId(cartItem.getCart().getCartId());
    cartItemDto.setCartItemId(cartItem.getCartItemId());
    cartItemDto.setQuantity(cartItem.getQuantity());
    cartItemDto.setUnitPrice(cartItem.getUnitPrice());
    cartItemDto.setTotalCost(cartItem.getTotalCost());
    cartItemDto.setCreatedAt(cartItem.getCreatedAt());

    cartItemDto.setProductId(cartItem.getProductId());
    cartItemDto.setProductName(cartItem.getProductName());
    cartItemDto.setProductImage(cartItem.getProductImage());

    return cartItemDto;
  }
}
