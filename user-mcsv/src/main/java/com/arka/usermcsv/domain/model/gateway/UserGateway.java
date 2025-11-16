package com.arka.usermcsv.domain.model.gateway;

import com.arka.usermcsv.domain.model.User;

import java.util.Optional;

public interface UserGateway {
  User createUser(User user);
  User updateUser(User user);
  Optional<User> findById(Long userId);
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);
}
