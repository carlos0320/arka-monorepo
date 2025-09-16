package com.arka.usermcsv.services;

import com.arka.usermcsv.dtos.UserRequestDto;
import com.arka.usermcsv.dtos.UserResponseDto;

public interface IUserService {

   public void createUser(UserRequestDto userRequestDto);
}
