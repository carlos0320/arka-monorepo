package com.arka.cartmcsv.infrastructure.adapter.clients.users;

import com.arka.cartmcsv.domain.model.Client;
import com.arka.cartmcsv.domain.model.User;
import com.arka.cartmcsv.domain.model.gateway.UserCacheGateway;
import com.arka.cartmcsv.domain.model.gateway.UserGateway;
import com.arka.cartmcsv.infrastructure.adapter.clients.users.dto.UserResponseDto;

public class UserGatewayImpl implements UserGateway {

  private final UserHttpClient userHttpClient;

  public UserGatewayImpl(UserHttpClient userHttpClient) {
    this.userHttpClient = userHttpClient;
  }

  @Override
  public User getUserDetails(Long userId) {
    UserResponseDto userResponse = userHttpClient.getUserDetails(String.valueOf(userId));
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
}
