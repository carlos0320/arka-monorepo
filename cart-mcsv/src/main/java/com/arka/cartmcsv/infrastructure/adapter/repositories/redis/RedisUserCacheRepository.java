//package com.arka.cartmcsv.infrastructure.adapter.repositories.redis;
//
//import com.arka.cartmcsv.domain.model.User;
//import com.arka.cartmcsv.domain.model.gateway.UserCacheGateway;
//import com.arka.cartmcsv.infrastructure.adapter.repositories.redis.dto.UserCachedDto;
//import com.arka.cartmcsv.infrastructure.adapter.repositories.redis.mapper.UserCachedMapper;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.time.Duration;
//import java.util.Optional;
//
//@Repository
//public class RedisUserCacheRepository implements UserCacheGateway {
//  private final RedisTemplate<String, UserCachedDto> redisTemplate;
//  private static final Duration TTL = Duration.ofDays(1);
//
//  public RedisUserCacheRepository(RedisTemplate<String, UserCachedDto> redisTemplate) {
//    this.redisTemplate = redisTemplate;
//  }
//
//  @Override
//  public Optional<User> getCachedUser(Long userId) {
//    UserCachedDto userCached = redisTemplate.opsForValue().get("user:" + userId);
//    return Optional.ofNullable(userCached != null ? UserCachedMapper.toDomain(userCached) : null);
//  }
//
//  @Override
//  public void cacheUser(Long userId, User user) {
//    redisTemplate.opsForValue().set("user:" + userId, UserCachedMapper.toDto(user), TTL);
//  }
//}
