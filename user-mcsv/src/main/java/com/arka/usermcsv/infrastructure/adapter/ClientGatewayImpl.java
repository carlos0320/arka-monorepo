package com.arka.usermcsv.infrastructure.adapter;

import com.arka.usermcsv.infrastructure.mapper.ClientMapper;
import com.arka.usermcsv.domain.model.Client;
import com.arka.usermcsv.domain.model.gateway.ClientGateway;
import com.arka.usermcsv.infrastructure.entity.ClientEntity;
import com.arka.usermcsv.infrastructure.repository.ClientRepository;

import java.util.Optional;

public class ClientGatewayImpl implements ClientGateway {

  private final ClientRepository clientRepository;
  private final ClientMapper clientMapper;

  public ClientGatewayImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
    this.clientRepository = clientRepository;
    this.clientMapper = clientMapper;
  }

  @Override
  public Client save(Client client) {
    ClientEntity clientEntity = clientRepository.save(clientMapper.toEntity(client));
    return clientMapper.toDomain(clientEntity);
  }

  @Override
  public Optional<Client> findByName(String name) {
    return clientRepository.findByCustomerNameIgnoreCase(name)
            .map(entity -> clientMapper.toDomain(entity));
  }

  @Override
  public Optional<Client> findById(Long id) {
    return Optional.empty();
  }

  @Override
  public Optional<Client> findByUserId(Long userId) {
    return Optional.empty();
  }
}
