package com.arka.cartmcsv.infrastructure.adapter.clients.users;

import com.arka.cartmcsv.domain.exceptions.UserNotFoundException;
import com.arka.cartmcsv.domain.exceptions.UserServiceException;
import com.arka.cartmcsv.domain.model.Client;
import com.arka.cartmcsv.domain.model.User;
import com.arka.cartmcsv.domain.model.gateway.UserCacheGateway;
import com.arka.cartmcsv.domain.model.gateway.UserGateway;
import com.arka.cartmcsv.infrastructure.adapter.clients.users.dto.UserResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public class UserGatewayImpl implements UserGateway {

  private final UserHttpClient userHttpClient;

  public UserGatewayImpl(UserHttpClient userHttpClient) {
    this.userHttpClient = userHttpClient;
  }

  @Override
  @Retryable(
          maxAttempts = 3,
          backoff = @Backoff(delay = 500, multiplier = 2)
  )
  @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetUser")
  public User getUserDetails(Long userId) {
    UserResponseDto userResponse = userHttpClient.getUserDetails(String.valueOf(userId));

    if (userResponse == null || userResponse.getData() == null) {
      throw new UserNotFoundException(userId);
    }

    User user = new User();
    user.setUserId(userId);
    user.setAddress(userResponse.getData().getAddress());
    user.setName(userResponse.getData().getName());
    user.setPhone(userResponse.getData().getPhone());
    user.setEmail(userResponse.getData().getEmail());

    Client client = new Client();
    client.setClientId(userResponse.getData().getClient().getClientId());
    client.setCustomerAddress(userResponse.getData().getClient().getCustomerAddress());
    client.setCustomerName(userResponse.getData().getClient().getCustomerName());

    user.setClient(client);
    return user;
  }

  public User fallbackGetUser(Long userId, Throwable ex) {
    throw new UserServiceException("User service is temporarily unavailable");
  }
}
