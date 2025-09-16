package com.arka.usermcsv.mappers;

import com.arka.usermcsv.dtos.UserRequestDto;
import com.arka.usermcsv.dtos.UserResponseDto;
import com.arka.usermcsv.entities.User;

public class UserMapper {

   public static User toUser(UserRequestDto userRequestDto){
      User user = new User();
      user.setName(userRequestDto.getName());
      user.setEmail(userRequestDto.getEmail());
      user.setPhone(userRequestDto.getPhone());
      user.setPassword(userRequestDto.getPassword());
      return user;
   }

   public static UserResponseDto toUserResponseDto(User user){
      UserResponseDto userResponseDto = new UserResponseDto();
      userResponseDto.setName(user.getName());
      userResponseDto.setEmail(user.getEmail());
      userResponseDto.setPhone(user.getPhone());
      userResponseDto.setUserId(user.getUserId());
      userResponseDto.setActive(user.isActive());
      return userResponseDto;
   }
}
