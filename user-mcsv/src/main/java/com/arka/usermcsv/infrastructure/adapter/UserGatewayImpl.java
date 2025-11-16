package com.arka.usermcsv.infrastructure.adapter;

import com.arka.usermcsv.infrastructure.mapper.UserMapper;
import com.arka.usermcsv.domain.model.User;
import com.arka.usermcsv.domain.model.gateway.UserGateway;
import com.arka.usermcsv.infrastructure.entity.UserEntity;
import com.arka.usermcsv.infrastructure.repository.UserRepository;

import java.util.Optional;

public class UserGatewayImpl implements UserGateway {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserGatewayImpl(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Override
  public User createUser(User user) {
    UserEntity entity = userMapper.toEntity(user);
    return userMapper.toDomain(userRepository.save(entity));
  }

  @Override
  public User updateUser(User user) {
    UserEntity entity = userMapper.toEntity(user);
    return userMapper.toDomain(userRepository.save(entity));
  }

  @Override
  public Optional<User> findById(Long userId) {
    return userRepository.findById(userId)
            .map(userMapper::toDomain);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmailIgnoreCase(email)
            .map(userMapper::toDomain);
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.findByEmailIgnoreCase(email).isPresent();
  }
}
