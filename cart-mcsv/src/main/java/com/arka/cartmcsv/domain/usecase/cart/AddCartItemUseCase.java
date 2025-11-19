package com.arka.cartmcsv.domain.usecase.cart;

import com.arka.cartmcsv.domain.exceptions.*;
import com.arka.cartmcsv.domain.model.*;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;
import com.arka.cartmcsv.domain.model.gateway.UserGateway;
import com.arka.cartmcsv.domain.usecase.inventory.ReserveStockUseCase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AddCartItemUseCase {
  private final CartGateway cartGateway;
  private final ReserveStockUseCase reserveStockUseCase;
  private final UserGateway userGateway;

  public AddCartItemUseCase(
          CartGateway cartGateway,
          ReserveStockUseCase reserveStockUseCase, UserGateway userGateway) {
    this.cartGateway = cartGateway;
    this.reserveStockUseCase = reserveStockUseCase;
    this.userGateway = userGateway;
  }

  public void execute(Long userId, Long productId, Integer quantity) {

    // validations
    validateInputs(userId,productId,quantity);

    // get cart to associate cart item and user
    Cart cart = cartGateway.findCartByUserIdAndStatuses( userId, List.of(CartStatus.PENDING, CartStatus.ABANDONED))
            .orElseGet(() -> {
              // get user details
              User user = userGateway.getUserDetails(userId);

              Cart newCart = new Cart();
              newCart.setTotalCost(BigDecimal.ZERO);
              newCart.setStatus(CartStatus.PENDING.getValue());

              newCart.setUser(user);

              newCart.setItems(new ArrayList<>());
              newCart.setCreatedAt(LocalDateTime.now());
              return  newCart;
            });

    if (!cart.isPending() && !cart.isAbandoned()){
      throw new InvalidCartStatusTransitionException("Cart status must be pending or abandoned to add a new item");
    }

    // reserve stock
    Product product = reserveStockUseCase.execute(productId, quantity);

    // add new item to cart
    cart.addNewItem(product, quantity);

    // save cart
    cartGateway.save(cart);
  }

  private void validateInputs(Long userId, Long productId, Integer quantity){
    if (userId == null || userId <= 0 ){
      throw new InvalidUserIdException();
    }

    if (productId == null || productId <= 0){
      throw new InvalidProductIdException();
    }

    if (quantity == null || quantity <= 0){
      throw  new InvalidQuantityException();
    }
  }
}