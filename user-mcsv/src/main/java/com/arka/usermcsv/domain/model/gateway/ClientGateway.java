package com.arka.usermcsv.domain.model.gateway;

import com.arka.usermcsv.domain.model.Client;

import java.util.Optional;

public interface ClientGateway {
  Client save(Client client);
  Optional<Client> findByName(String name);
  Optional<Client> findById(Long id);
  Optional<Client> findByUserId(Long userId);
}
