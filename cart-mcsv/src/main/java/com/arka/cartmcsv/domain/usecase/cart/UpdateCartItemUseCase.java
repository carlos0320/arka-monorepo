package com.arka.cartmcsv.domain.usecase.cart;

import com.arka.cartmcsv.domain.exceptions.InvalidCartItemIdExceptions;
import com.arka.cartmcsv.domain.exceptions.InvalidProductIdException;
import com.arka.cartmcsv.domain.exceptions.InvalidQuantityException;
import com.arka.cartmcsv.domain.exceptions.InvalidUserIdException;
import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartItem;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.domain.model.Product;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;
import com.arka.cartmcsv.domain.usecase.inventory.ReleaseStockUseCase;
import com.arka.cartmcsv.domain.usecase.inventory.ReserveStockUseCase;

import java.util.List;

public class UpdateCartItemUseCase {
  private final CartGateway cartGateway;
  private final ReleaseStockUseCase releaseStockUseCase;
  private final ReserveStockUseCase reserveStockUseCase;

  public UpdateCartItemUseCase(CartGateway cartGateway,
                               ReleaseStockUseCase releaseStockUseCase,
                               ReserveStockUseCase reserveStockUseCase) {
    this.cartGateway = cartGateway;
    this.releaseStockUseCase = releaseStockUseCase;
    this.reserveStockUseCase = reserveStockUseCase;
  }

  public void execute(Long userId, Long productId, Long cartItemId, Integer quantity) {
    validateInputs(userId, productId, cartItemId, quantity);

    Cart cart = cartGateway.findCartByUserIdAndStatuses( userId, List.of(CartStatus.PENDING, CartStatus.ABANDONED))
            .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

    CartItem cartItem = cart.findCartItemById(cartItemId);

    int deltaQuantity = Math.abs(cartItem.getQuantity() - quantity);

    if (cartItem.getQuantity() < quantity) {
      reserveStockUseCase.execute(productId,deltaQuantity);
    }else{
      releaseStockUseCase.execute(productId,deltaQuantity);
    }

    cart.updateCartItem(cartItem, quantity);

    cartGateway.save(cart);
  }

  private void validateInputs(Long userId, Long productId, Long cartItemId, Integer quantity) {
    if (userId == null || userId <= 0)
      throw new InvalidUserIdException();
    if (cartItemId == null || cartItemId <= 0)
      throw new InvalidCartItemIdExceptions();
    if (productId == null || productId <= 0)
      throw new InvalidProductIdException();
    if (quantity == null || quantity <= 0)
      throw new InvalidQuantityException();
  }
}

