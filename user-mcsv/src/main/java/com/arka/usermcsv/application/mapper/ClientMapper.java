package com.arka.usermcsv.application.mapper;

import com.arka.usermcsv.application.dto.ClientDto;
import com.arka.usermcsv.domain.model.Client;

public class ClientMapper {
  public static Client toDomain(ClientDto dto) {
    if (dto == null) return null;
    Client client = new Client();
    client.setCustomerName(dto.getCustomerName());
    client.setCustomerAddress(dto.getCustomerAddress());
    return client;
  }

  public static ClientDto toDto(Client client) {
    if (client == null) return null;
    ClientDto dto = new ClientDto();
    dto.setClientId(client.getClientId());
    dto.setCustomerName(client.getCustomerName());
    dto.setCustomerAddress(client.getCustomerAddress());
    return dto;
  }
}
