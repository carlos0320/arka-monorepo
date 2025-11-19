package com.arka.cartmcsv.infrastructure.controller;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.usecase.cart.*;
import com.arka.cartmcsv.infrastructure.controller.dto.CartDto;
import com.arka.cartmcsv.infrastructure.controller.dto.CartRequestDto;
import com.arka.cartmcsv.infrastructure.controller.dto.ResponseDto;
import com.arka.cartmcsv.infrastructure.controller.mapper.CartDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {
  private final AddCartItemUseCase addCartItemUseCase;
  private final UpdateCartItemUseCase updateCartItemUseCase;
  private final GetUserCartUseCase getUserCartUseCase;
  private final RemoveCartItemUseCase removeCartItemUseCase;

  private final ConfirmCartUseCase confirmCartUseCase;
  private final CancelCartUseCase cancelCartUseCase;

  private final GetAbandonedCartsUseCase getAbandonedCartsUseCase;

  public CartController(
          AddCartItemUseCase addCartItemUseCase,
          UpdateCartItemUseCase updateCartItemUseCase,
          GetUserCartUseCase getUserCartUseCase,
          RemoveCartItemUseCase removeCartItemUseCase,
          ConfirmCartUseCase confirmCartUseCase,
          CancelCartUseCase cancelCartUseCase, GetAbandonedCartsUseCase getAbandonedCartsUseCase
  ) {
    this.addCartItemUseCase = addCartItemUseCase;
    this.updateCartItemUseCase = updateCartItemUseCase;
    this.getUserCartUseCase = getUserCartUseCase;
    this.removeCartItemUseCase = removeCartItemUseCase;
    this.confirmCartUseCase = confirmCartUseCase;
    this.cancelCartUseCase = cancelCartUseCase;
    this.getAbandonedCartsUseCase = getAbandonedCartsUseCase;
  }

  @PostMapping("/cart-item")
  public ResponseEntity<ResponseDto> addCartItem(
          @RequestHeader("X-User-ID") String userId,
          @RequestHeader("X-User-roles") String roles,
          @Valid @RequestBody CartRequestDto cartRequestDto
  ){

    if (!roles.contains("CLIENT")){
      return throwForbiddenError();
    }

    addCartItemUseCase.execute(Long.valueOf(userId), cartRequestDto.getProductId(),cartRequestDto.getQuantity());

    ResponseDto<String> responseDto = new ResponseDto<>();
    responseDto.setData("Cart added successfully");
    responseDto.setStatus(HttpStatus.OK);

    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }

  @GetMapping
  public ResponseEntity<ResponseDto> getUserCart(
          @RequestHeader("X-User-ID") String userId,
          @RequestHeader("X-User-roles") String roles
  ) {

    if (!roles.contains("CLIENT")){
      return throwForbiddenError();
    }

    Cart userCart = getUserCartUseCase.execute(Long.valueOf(userId));

    ResponseDto<CartDto> responseDto = new ResponseDto<>();
    responseDto.setData(CartDtoMapper.toDto(userCart));
    responseDto.setStatus(HttpStatus.OK);

    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }

  @PatchMapping("/cart-item/{cartItemId}")
  public ResponseEntity<ResponseDto> updateCartItem(
          @RequestHeader("X-User-ID") String userId,
          @RequestHeader("X-User-roles") String roles,
          @PathVariable("cartItemId") String cartItemId,
          @Valid @RequestBody CartRequestDto cartRequestDto
  )
  {

    if (!roles.contains("CLIENT")){
      throwForbiddenError();
    }

    updateCartItemUseCase.execute(Long.valueOf(userId), cartRequestDto.getProductId(), Long.valueOf(cartItemId),cartRequestDto.getQuantity());

    ResponseDto<String> responseDto = new ResponseDto<>();
    responseDto.setData("Cart updated successfully");
    responseDto.setStatus(HttpStatus.OK);

    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }

  @DeleteMapping("/cart-item/{cartItemId}")
  public ResponseEntity<ResponseDto> deleteCartItem(
          @RequestHeader("X-User-ID") String userId,
          @RequestHeader("X-User-roles") String roles,
          @PathVariable("cartItemId") String cartItemId
  ){

    if (!roles.contains("CLIENT")){
      return throwForbiddenError();
    }

    removeCartItemUseCase.execute(Long.valueOf(userId),Long.valueOf(cartItemId));
    return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseDto(
                    Map.of("message", "Item removed successfully"),
                    HttpStatus.OK
            ));
  }

  @PostMapping("/confirm")
  public ResponseEntity<ResponseDto> confirmCart(
          @RequestHeader("X-User-ID") String userId,
          @RequestHeader("X-User-roles") String roles
  ){

    if (!roles.contains("CLIENT")){
      return throwForbiddenError();
    }

    confirmCartUseCase.execute(Long.valueOf(userId));
    return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseDto(
                    Map.of("message", "Cart confirmed successfully"),
                    HttpStatus.OK
            ));
  }

  @PostMapping("/cancel")
  public ResponseEntity<ResponseDto> cancelCart(
          @RequestHeader("X-User-ID") String userId,
          @RequestHeader("X-User-roles") String roles
  ){

    if (!roles.contains("CLIENT")){
      return throwForbiddenError();
    }

    cancelCartUseCase.execute(Long.valueOf(userId));
    return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseDto(
                    Map.of("message", "Cart cancelled successfully"),
                    HttpStatus.OK
            ));
  }

  @GetMapping("/admin/abandoned-carts")
  public ResponseEntity<ResponseDto> getAbandonedCarts(
          @RequestHeader("X-User-roles") String roles
  ){
    if (!roles.contains("ADMIN")){
      return throwForbiddenError();
    }

    List<CartDto> abandonedCarts = getAbandonedCartsUseCase.execute()
            .stream()
            .map(CartDtoMapper::toDto)
            .collect(Collectors.toList());

    ResponseDto<String> responseDto = new ResponseDto<>();
    responseDto.setData("Cart updated successfully");
    responseDto.setStatus(HttpStatus.OK);

    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }


  private ResponseEntity<ResponseDto> throwForbiddenError(){
    ResponseDto<String> responseDto = new ResponseDto<>();
    responseDto.setData("Only users with client role are allowed to perform this action");
    responseDto.setStatus(HttpStatus.FORBIDDEN);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto);
  }

}
