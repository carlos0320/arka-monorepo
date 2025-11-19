package com.arka.cartmcsv.infrastructure.adapter.repositories.cart;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.domain.model.CartStatus;
import com.arka.cartmcsv.domain.model.gateway.CartGateway;
import com.arka.cartmcsv.infrastructure.adapter.entities.CartEntity;
import com.arka.cartmcsv.infrastructure.adapter.mappers.CartEntityMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CartGatewayImpl implements CartGateway {
  private final CartEntityRepository cartEntityRepository;

  public CartGatewayImpl(CartEntityRepository cartEntityRepository) {
    this.cartEntityRepository = cartEntityRepository;
  }

  @Override
  public Cart save(Cart cart) {
    CartEntity cartEntity = CartEntityMapper.toEntity(cart);
    cartEntityRepository.save(cartEntity);
    return CartEntityMapper.toDomain(cartEntity);
  }


  @Override
  public void updateCart(Cart cart) {
    CartEntity cartEntity = CartEntityMapper.toEntity(cart);
    cartEntityRepository.save(cartEntity);
  }


  @Override
  public Optional<Cart> findCartByUserIdAndStatuses(Long userId, List<CartStatus> statuses) {
    List<String> statusValues = statuses.stream()
            .map(CartStatus::getValue)
            .toList();

    System.out.println("Status values: " + statusValues);

    Optional<Cart> cartopt = cartEntityRepository.findByUserIdAndStatusIn(userId, statusValues)
            .map(cartEntity -> {
              System.out.println("Cart found: " + cartEntity.getCartId()); // print safe field
              return CartEntityMapper.toDomain(cartEntity);
            });

    System.out.println("sssdsdsssdsd" + cartopt);
    return cartEntityRepository.findByUserIdAndStatusIn(userId, statusValues)
            .map(cartEntity -> {
              System.out.println("Cart found: " + cartEntity.getCartId()); // print safe field
              return CartEntityMapper.toDomain(cartEntity);
            });
  }

  @Override
  public void removeCart(Long cartId) {
    cartEntityRepository.deleteById(cartId);
  }

  @Override
  public List<Cart> getAbandonedCarts() {
    return cartEntityRepository.findAbandonedCarts()
            .stream()
            .map(CartEntityMapper::toDomain)
            .collect(Collectors.toList());
  }
}
