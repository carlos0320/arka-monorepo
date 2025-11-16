package com.arka.cartmcsv.domain.usecase.user;

import com.arka.cartmcsv.domain.model.User;
import com.arka.cartmcsv.domain.model.gateway.UserCacheGateway;
import com.arka.cartmcsv.domain.model.gateway.UserGateway;

public class GetUserDetailsUseCase {
  private final UserCacheGateway userCacheGateway;
  private final UserGateway userGateway;


  public GetUserDetailsUseCase(UserCacheGateway userCacheGateway, UserGateway userGateway) {
    this.userCacheGateway = userCacheGateway;
    this.userGateway = userGateway;
  }

  public User execute(Long userId){
    return userCacheGateway.getCachedUser(userId)
            .orElseGet(() -> {
              User user =  userGateway.getUserDetails(userId);
              userCacheGateway.cacheUser(userId, user);
              return user;
            });
  }
}
