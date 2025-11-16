package com.arka.ordermcsv.infrastructure.controller;

import com.arka.ordermcsv.domain.usecases.ConfirmOrderUseCase;
import com.arka.ordermcsv.domain.usecases.ShipOrderUseCase;
import com.arka.ordermcsv.infrastructure.controller.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final ConfirmOrderUseCase orderConfirmedUseCase;
  private final ShipOrderUseCase orderShippedUseCase;

  public OrderController(ConfirmOrderUseCase orderConfirmedUseCase, ShipOrderUseCase orderShippedUseCase) {
    this.orderConfirmedUseCase = orderConfirmedUseCase;
    this.orderShippedUseCase = orderShippedUseCase;
  }

  @PostMapping("/admin/confirm/{orderId}")
  public ResponseEntity<ResponseDto<String>> confirmOrder(
          @PathVariable Long orderId,
          @RequestHeader("X-User-Roles") String roles
  ) {
    if (!roles.contains("ADMIN")) {
      throwForbiddenError(); // or return 403 response
    }

    orderConfirmedUseCase.execute(orderId);

    ResponseDto<String> responseDto = new ResponseDto<>();
    responseDto.setData("Order confirmed successfully");
    responseDto.setStatus(HttpStatus.OK);
    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }

  @PostMapping("/admin/ship/{orderId}")
  public ResponseEntity<ResponseDto<String>> shipOrder(
          @PathVariable Long orderId,
          @RequestHeader("X-User-Roles") String roles
  ) {
    if (!roles.contains("ADMIN")) {
      throwForbiddenError(); // or return 403 response
    }

    orderShippedUseCase.execute(orderId);

    ResponseDto<String> responseDto = new ResponseDto<>();
    responseDto.setData("Order shipped successfully");
    responseDto.setStatus(HttpStatus.OK);
    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }


  private ResponseEntity<ResponseDto> throwForbiddenError(){
    ResponseDto<String> responseDto = new ResponseDto<>();
    responseDto.setData("Only users with  role admin are allowed to perform this action");
    responseDto.setStatus(HttpStatus.FORBIDDEN);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto);
  }


}
