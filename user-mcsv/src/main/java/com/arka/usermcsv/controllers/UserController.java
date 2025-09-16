package com.arka.usermcsv.controllers;

import com.arka.usermcsv.dtos.ResponseDto;
import com.arka.usermcsv.dtos.UserRequestDto;
import com.arka.usermcsv.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

   private final IUserService userService;

   @PostMapping("/register")
   public ResponseEntity<ResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
      userService.createUser(userRequestDto);
      return ResponseEntity.ok(new ResponseDto("User created successfully", HttpStatus.CREATED));
   }
}
