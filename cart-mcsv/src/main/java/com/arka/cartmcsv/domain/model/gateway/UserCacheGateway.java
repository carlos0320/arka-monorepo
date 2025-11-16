package com.arka.cartmcsv.domain.model.gateway;

import com.arka.cartmcsv.domain.model.User;

import java.util.Optional;

public interface UserCacheGateway {
  Optional<User> getCachedUser(Long userId);
  void cacheUser(Long userId, User user);
}
