package com.arka.cartmcsv.infrastructure.controller.mapper;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.infrastructure.adapter.mappers.CartItemEntityMapper;
import com.arka.cartmcsv.infrastructure.controller.dto.CartDto;
import com.arka.cartmcsv.infrastructure.controller.dto.CartItemDto;
import com.arka.cartmcsv.infrastructure.controller.dto.ClientDataDto;
import com.arka.cartmcsv.infrastructure.controller.dto.UserDataDto;

import java.util.List;
import java.util.stream.Collectors;

public class CartDtoMapper {
  public static CartDto toDto(Cart cart){
    CartDto cartDto = new CartDto();
    cartDto.setCartId(cart.getCartId());
    cartDto.setCreatedAt(cart.getCreatedAt());
    cartDto.setTotalCost(cart.getTotalCost());

    if (cart.getUser() != null){
      UserDataDto userDataDto = new UserDataDto();
      userDataDto.setUserId(cart.getUser().getUserId());
      userDataDto.setUserPhone(cart.getUser().getPhone());
      userDataDto.setUserName(cart.getUser().getName());
      userDataDto.setUserEmail(cart.getUser().getEmail());
      userDataDto.setUserAddress(cart.getUser().getAddress());

      ClientDataDto clientDataDto = new ClientDataDto();
      clientDataDto.setClientId(cart.getUser().getClient().getClientId());
      clientDataDto.setCustomerName(cart.getUser().getClient().getCustomerName());
      userDataDto.setClientData(clientDataDto);

      cartDto.setUserData(userDataDto);
    }

    if (cart.getItems() != null){
      List<CartItemDto> cartItemsDto = cart.getItems()
              .stream()
              .map(CartItemDtoMapper::toDto)
              .collect(Collectors.toList());

      cartDto.setItems(cartItemsDto);
    }

    return cartDto;

  }

}
