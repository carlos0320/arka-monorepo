package com.arka.cartmcsv.domain.usecase.cart;

import com.arka.cartmcsv.domain.exceptions.CartNotFoundException;
import com.arka.cartmcsv.domain.exceptions.InvalidUserIdException;
import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartItem;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;
import com.arka.cartmcsv.domain.usecase.inventory.ReleaseStockUseCase;

import java.util.List;

public class RemoveCartItemUseCase {
  private final CartGateway cartGateway;
  private final ReleaseStockUseCase releaseStockUseCase;


  public RemoveCartItemUseCase(CartGateway cartGateway, ReleaseStockUseCase releaseStockUseCase) {
    this.cartGateway = cartGateway;
    this.releaseStockUseCase = releaseStockUseCase;
  }

  public void execute(Long userId, Long cartItemId){
    validateInputs(userId,cartItemId);
    Cart cart = cartGateway.findCartByUserIdAndStatuses( userId, List.of(CartStatus.PENDING, CartStatus.ABANDONED))
            .orElseThrow(() -> new CartNotFoundException());

    CartItem cartItem = cart.findCartItemById(cartItemId);

    releaseStockUseCase.execute(cartItem.getProductId(), cartItem.getQuantity());

    cart.removeCartItem(cartItemId);

    cartGateway.save(cart);
  }

  private void validateInputs(Long userId,  Long cartItemId) {

    if (userId == null || userId <= 0)
      throw new InvalidUserIdException();

    if (cartItemId == null || cartItemId <= 0)
      throw new InvalidUserIdException();
  }
}
