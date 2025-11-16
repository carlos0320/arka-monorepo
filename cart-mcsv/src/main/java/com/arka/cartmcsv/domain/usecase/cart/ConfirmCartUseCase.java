package com.arka.cartmcsv.domain.usecase.cart;


import com.arka.cartmcsv.domain.event.CartConfirmEvent;
import com.arka.cartmcsv.domain.event.OutboxEvent;
import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;
import com.arka.cartmcsv.domain.event.gateway.OutboxEventGateway;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        throw new IllegalArgumentException("User id is required and must be valid");
      }

      Cart cart = cartGateway.findCartByUserIdAndStatus(userId, CartStatus.PENDING)
              .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

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
