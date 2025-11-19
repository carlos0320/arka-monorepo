package com.arka.cartmcsv.domain.usecase.cart;

import com.arka.cartmcsv.domain.event.OutboxEvent;
import com.arka.cartmcsv.domain.event.gateway.OutboxEventGateway;
import com.arka.cartmcsv.domain.exceptions.CartNotFoundException;
import com.arka.cartmcsv.domain.exceptions.InvalidUserIdException;
import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class CancelCartUseCase {
  private final CartGateway cartGateway;
  private final OutboxEventGateway outboxGateway;
  private final ObjectMapper objectMapper;

  public CancelCartUseCase(CartGateway cartGateway, OutboxEventGateway outboxGateway, ObjectMapper objectMapper) {
    this.cartGateway = cartGateway;
    this.outboxGateway = outboxGateway;
    this.objectMapper = objectMapper;
  }

  public void execute(Long userId){
    try{
      if (userId == null || userId <= 0){
        throw new InvalidUserIdException();
      }

      Cart cart = cartGateway.findCartByUserIdAndStatuses( userId, List.of(CartStatus.PENDING, CartStatus.ABANDONED))
              .orElseThrow(() -> new CartNotFoundException());

      cart.cancel();
      cartGateway.save(cart);

      // outbox pattern
      String payload = objectMapper.writeValueAsString(cart);

      OutboxEvent outboxEvent = OutboxEvent.from(
              "Cart",
              cart.getCartId(),
              "CartCancelledEvent",
              payload
      );

      outboxGateway.save(outboxEvent);

    }catch (Exception e){
      throw new RuntimeException("Failed to save outbox event: " + e.getMessage(), e);
    }
  }
}
