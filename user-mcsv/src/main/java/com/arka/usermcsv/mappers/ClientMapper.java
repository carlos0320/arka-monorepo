package com.arka.usermcsv.mappers;

import com.arka.usermcsv.dtos.ClientDto;
import com.arka.usermcsv.entities.Client;

public class ClientMapper {

   public static Client toClient(ClientDto clientDto){
      Client client = new Client();
      client.setCustomerName(clientDto.getCustomerName());
      client.setCustomerAddress(clientDto.getCustomerAddress());
      return client;
   }

   public static ClientDto toClientDto(Client client){
      ClientDto clientDto = new ClientDto();
      clientDto.setCustomerAddress(client.getCustomerAddress());
      clientDto.setCustomerName(client.getCustomerName());
      return clientDto;
   }
}
