package com.arka.usermcsv.infrastructure.controller;


import com.arka.usermcsv.application.dto.ResponseDto;
import com.arka.usermcsv.application.dto.UserResponseDto;
import com.arka.usermcsv.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDto<UserResponseDto>> getUserById(@PathVariable String id) {
    return ResponseEntity.ok(new ResponseDto<>(userService.getDetails(Long.valueOf(id))));
  }

  @GetMapping("/internal/{id}")
  public ResponseEntity<ResponseDto<UserResponseDto>> getUserDetails(@PathVariable String id) {
    return ResponseEntity.ok(new ResponseDto<>(userService.getDetails(Long.valueOf(id))));
  }
}