package com.arka.cartmcsv.domain.usecase.cart;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartItem;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.domain.model.Product;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;
import com.arka.cartmcsv.domain.usecase.inventory.ReleaseStockUseCase;
import com.arka.cartmcsv.domain.usecase.inventory.ReserveStockUseCase;

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

    Cart cart = cartGateway.findCartByUserIdAndStatus(userId, CartStatus.PENDING)
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
      throw new IllegalArgumentException("Invalid user id");
    if (cartItemId == null || cartItemId <= 0)
      throw new IllegalArgumentException("Invalid cart item id");
    if (productId == null || productId <= 0)
      throw new IllegalArgumentException("Invalid product id");
    if (quantity == null || quantity <= 0)
      throw new IllegalArgumentException("Invalid quantity");
  }
}

