package com.arka.usermcsv.application.mapper;

import com.arka.usermcsv.application.dto.RoleDto;
import com.arka.usermcsv.application.dto.UserRequestDto;
import com.arka.usermcsv.application.dto.UserResponseDto;
import com.arka.usermcsv.domain.model.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

  public static User toDomain(UserRequestDto dto, String encodedPassword){
    User user = new User();
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    user.setPassword(encodedPassword);
    user.setAddress(dto.getAddress());
    user.setPhone(dto.getPhone());
    user.setActive(true);
    return user;
  }

  public static UserResponseDto toDto(User user){
    UserResponseDto dto = new UserResponseDto();
    dto.setUserId(user.getUserId());
    dto.setName(user.getName());
    dto.setEmail(user.getEmail());
    dto.setAddress(user.getAddress());
    dto.setPhone(user.getPhone());
    dto.setActive(user.isActive());

    if (user.getRoles() != null) {
      Set<String> roles = user.getRoles()
              .stream()
              .map(r -> r.getRoleType().getValue())
              .collect(Collectors.toSet());
      dto.setRoles(roles);
    }

    if (user.getClient() != null) {
      dto.setClient(ClientMapper.toDto(user.getClient()));
    }

    if (user.getSupplier() != null) {
      dto.setSupplier(SupplierMapper.toDto(user.getSupplier()));
    }

    return dto;
  }

}
