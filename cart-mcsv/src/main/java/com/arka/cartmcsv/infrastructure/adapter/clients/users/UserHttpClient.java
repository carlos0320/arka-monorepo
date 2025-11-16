package com.arka.cartmcsv.infrastructure.adapter.clients.users;

import com.arka.cartmcsv.infrastructure.adapter.clients.users.dto.UserResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/users/internal")
public interface UserHttpClient {
  @GetExchange("/{userId}")
  UserResponseDto getUserDetails(@PathVariable("userId") String userId);
}
