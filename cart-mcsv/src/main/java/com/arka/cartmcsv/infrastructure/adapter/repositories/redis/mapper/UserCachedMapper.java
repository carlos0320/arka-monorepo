//package com.arka.cartmcsv.infrastructure.adapter.repositories.redis.mapper;
//
//import com.arka.cartmcsv.domain.model.Client;
//import com.arka.cartmcsv.domain.model.User;
//import com.arka.cartmcsv.infrastructure.adapter.repositories.redis.dto.UserCachedDto;
//
//public class UserCachedMapper {
//
//  public static User toDomain(UserCachedDto userCached) {
//    User user = new User();
//    user.setUserId(userCached.getUserId());
//    user.setAddress(userCached.getAddress());
//    user.setPhone(userCached.getPhone());
//    user.setEmail(userCached.getEmail());
//    Client client = new Client();
//    client.setClientId(userCached.getClientCached().getClientId());
//    client.setCustomerName(userCached.getClientCached().getCustomerName());
//    client.setCustomerAddress(userCached.getClientCached().getCustomerAddress());
//    user.setClient(client);
//    return user;
//  }
//
//  public static UserCachedDto toDto(User user){
//    UserCachedDto userCachedDto = new UserCachedDto();
//    userCachedDto.setAddress(user.getAddress());
//    userCachedDto.setPhone(user.getPhone());
//    userCachedDto.setEmail(user.getEmail());
//    userCachedDto.setUserId(user.getUserId());
//
//    UserCachedDto.ClientCachedDto clientCachedDto = new UserCachedDto.ClientCachedDto();
//    clientCachedDto.setClientId(user.getClient().getClientId());
//    clientCachedDto.setCustomerName(user.getClient().getCustomerName());
//    clientCachedDto.setCustomerAddress(user.getClient().getCustomerAddress());
//
//    userCachedDto.setClientCached(clientCachedDto);
//    return userCachedDto;
//  }
//}
