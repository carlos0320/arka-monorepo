package com.arka.cartmcsv.domain.model.gateway;

import com.arka.cartmcsv.domain.model.User;

public interface UserGateway {
  User getUserDetails(Long userId);
}
