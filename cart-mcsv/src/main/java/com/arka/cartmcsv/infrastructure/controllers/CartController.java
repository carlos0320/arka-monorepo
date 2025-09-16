package com.arka.cartmcsv.infrastructure.controllers;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartItem;
import com.arka.cartmcsv.domain.model.usecase.AddCartItemUseCase;
import com.arka.cartmcsv.domain.model.usecase.GetCartItemsUseCase;
import com.arka.cartmcsv.domain.model.usecase.UpdateCartItemUseCase;
import com.arka.cartmcsv.infrastructure.adapters.mappers.CartMapper;
import com.arka.cartmcsv.infrastructure.adapters.mappers.CartItemMapper;
import com.arka.cartmcsv.infrastructure.controllers.dtos.CartDto;
//import com.arka.cartmcsv.infrastructure.controllers.dtos.CartItemDto;
//import com.arka.cartmcsv.infrastructure.controllers.mappers.CartDtoMapper;
//import com.arka.cartmcsv.infrastructure.controllers.mappers.CartItemDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

  private final AddCartItemUseCase addCartItemUseCase;
  private final GetCartItemsUseCase getCartItemsUseCase;
  private final UpdateCartItemUseCase updateCartItemUseCase;

//  private final CartItemDtoMapper cartItemDtoMapper;
//  private final CartDtoMapper cartDtoMapper;

//  @PostMapping("/users/{userId}/items")
//  public ResponseEntity<CartDto> addCartItem(
//          @RequestBody CartItemDto cartItemDto,
//          @PathVariable Long userId) {
//
//    CartItem cartItem = cartItemDtoMapper.toDomain(cartItemDto);
//    Cart updatedCart = addCartItemUseCase.addCartItem(cartItem, userId);
//    return ResponseEntity.status(HttpStatus.CREATED).body(cartDtoMapper.toDto(updatedCart));
//  }

//  @GetMapping("/users/{userId}/items")
//  public ResponseEntity<CartDto> getCartItems(@PathVariable Long userId) {
//    Cart cart = getCartItemsUseCase.getCartItems(userId);
//    return ResponseEntity.ok(cartDtoMapper.toDto(cart));
//  }

//  @PatchMapping("/users/{userId}/items")
//  public ResponseEntity<CartDto> updateCartItem(
//          @RequestBody CartItemDto cartItemDto,
//          @PathVariable Long userId) {
//
//    var cartItem = cartItemDtoMapper.toDomain(cartItemDto);
//    var updatedCart = updateCartItemUseCase.updateCartItem(cartItem, userId);
//    return ResponseEntity.ok(cartDtoMapper.toDto(updatedCart));
//  }
}
