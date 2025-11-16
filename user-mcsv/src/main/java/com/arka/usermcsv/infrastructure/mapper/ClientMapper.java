package com.arka.usermcsv.infrastructure.mapper;

import com.arka.usermcsv.domain.model.Client;
import com.arka.usermcsv.domain.model.User;
import com.arka.usermcsv.infrastructure.entity.ClientEntity;
import com.arka.usermcsv.infrastructure.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class ClientMapper {
  public ClientEntity toEntity(Client client) {
    if (client == null) return null;
    ClientEntity entity = new ClientEntity();
    entity.setClientId(client.getClientId());
    entity.setCustomerName(client.getCustomerName());
    entity.setCustomerAddress(client.getCustomerAddress());
    return entity;
  }

  public Client toDomain(ClientEntity entity) {
    if (entity == null) return null;
    Client client = new Client();
    client.setClientId(entity.getClientId());
    client.setCustomerName(entity.getCustomerName());
    client.setCustomerAddress(entity.getCustomerAddress());
    client.setUserId(entity.getUser() != null ? entity.getUser().getUserId() : null);
    return client;
  }
}
