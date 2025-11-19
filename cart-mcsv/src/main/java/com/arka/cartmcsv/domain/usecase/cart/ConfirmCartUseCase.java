package com.arka.cartmcsv.domain.usecase.cart;


import com.arka.cartmcsv.domain.event.CartConfirmEvent;
import com.arka.cartmcsv.domain.event.OutboxEvent;
import com.arka.cartmcsv.domain.exceptions.CartNotFoundException;
import com.arka.cartmcsv.domain.exceptions.InvalidUserIdException;
import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;
import com.arka.cartmcsv.domain.event.gateway.OutboxEventGateway;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ConfirmCartUseCase {
  private final CartGateway cartGateway;
  private final OutboxEventGateway outboxGateway;
  private final ObjectMapper objectMapper;


  public ConfirmCartUseCase(CartGateway cartGateway, OutboxEventGateway outboxGateway, ObjectMapper objectMapper) {
    this.cartGateway = cartGateway;
    this.outboxGateway = outboxGateway;
    this.objectMapper = objectMapper;
  }

  public void execute(Long userId) {
    try{
      if (userId == null || userId <= 0){
        throw new InvalidUserIdException();
      }

      Cart cart = cartGateway.findCartByUserIdAndStatuses( userId, List.of(CartStatus.PENDING, CartStatus.ABANDONED))
              .orElseThrow(() -> new CartNotFoundException());

      cart.confirm();
      cartGateway.save(cart);

      // outbox pattern
      CartConfirmEvent cartConfirmEvent = CartConfirmEvent.fromCart(cart);
      String payload = objectMapper.writeValueAsString(cartConfirmEvent);

      OutboxEvent outboxEvent = OutboxEvent.from(
              "Cart",
              cart.getCartId(),
              "CartConfirmedEvent",
              payload
      );

      outboxGateway.save(outboxEvent);

    }catch (Exception e){
      throw new RuntimeException("Failed to save outbox event: " + e.getMessage(), e);
    }

  }
}
