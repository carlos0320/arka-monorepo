package com.arka.cartmcsv.infrastructure.adapters.mappers;

import com.arka.cartmcsv.domain.model.CartItem;
import com.arka.cartmcsv.infrastructure.adapters.entities.CartItemEntity;
import com.arka.cartmcsv.infrastructure.controllers.dtos.CartItemDto;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

  public static CartItem toDomain(CartItemEntity entity) {
    if (entity == null) return null;

    CartItem item = new CartItem();

    item.setShoppingCartItemId(entity.getShoppingCartItemId());
    item.setProductId(entity.getProductId());
    item.setQuantity(entity.getQuantity());
    item.setPrice(entity.getPrice());
    return item;
  }

  public static CartItemEntity toEntity(CartItem domain) {
    if (domain == null) return null;

    CartItemEntity entity = new CartItemEntity();
    entity.setShoppingCartItemId(domain.getShoppingCartItemId());
    entity.setProductId(domain.getProductId());
    entity.setQuantity(domain.getQuantity());
    entity.setPrice(domain.getPrice());
    return entity;
  }
}
