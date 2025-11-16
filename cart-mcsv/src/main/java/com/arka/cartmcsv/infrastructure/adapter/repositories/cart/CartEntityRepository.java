package com.arka.cartmcsv.infrastructure.adapter.repositories.cart;

import com.arka.cartmcsv.domain.model.Cart;
import com.arka.cartmcsv.infrastructure.adapter.entities.CartEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CartEntityRepository extends JpaRepository<CartEntity,Long> {
  Optional<CartEntity> findByCartId(Long cartId);
  Optional<CartEntity> findByUserIdAndStatus(Long userId, String status);

  @Query("SELECT c FROM CartEntity c WHERE c.status = 'abandoned'")
  List<CartEntity> findAbandonedCarts();

  @Modifying
  @Transactional
  @Query("UPDATE CartEntity c SET c.totalCost = :totalCost WHERE c.cartId = :cartId")
  void updateTotalCost(@Param("cartId") Long cartId, @Param("totalCost")  BigDecimal totalCost);
}
