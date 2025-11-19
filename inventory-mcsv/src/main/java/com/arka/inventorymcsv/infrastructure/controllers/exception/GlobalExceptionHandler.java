package com.arka.inventorymcsv.infrastructure.controllers.exception;

import com.arka.inventorymcsv.domain.exceptions.BusinessException;
import com.arka.inventorymcsv.domain.exceptions.NotFoundException;
import com.arka.inventorymcsv.domain.exceptions.ValidationException;
import com.arka.inventorymcsv.infrastructure.controllers.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(WebExchangeBindException.class)
  public Mono<ResponseEntity<ResponseDto>> handleValidationErrors(WebExchangeBindException ex) {
    Map<String, Object> data = new HashMap<>();
    data.put("error", "Validation failed");
    data.put("details", ex.getFieldErrors()
            .stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .collect(Collectors.toList()));

    ResponseDto responseDto = new ResponseDto();
    responseDto.setStatus(HttpStatus.BAD_REQUEST);
    responseDto.setData(data);

    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto));
  }

  @ExceptionHandler(ValidationException.class)
  public Mono<ResponseEntity<ResponseDto>> handleValidationException(ValidationException ex) {
    return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(BusinessException.class)
  public Mono<ResponseEntity<ResponseDto>> handleBusinessException(BusinessException ex) {
    return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  public Mono<ResponseEntity<ResponseDto>> handleNotFoundException(NotFoundException ex) {
    return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<ResponseDto>> handleException(Exception ex) {
    ex.printStackTrace();
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error: " + ex.getMessage());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String,String>> handleJsonParseError(HttpMessageNotReadableException ex){
    Map<String,String> error = new HashMap<>();
    error.put("error", "Invalid JSON input: " + ex.getMostSpecificCause().getMessage());
    return ResponseEntity.badRequest().body(error);
  }

  private Mono<ResponseEntity<ResponseDto>> buildResponse(HttpStatus status, String message) {
    Map<String, Object> data = new HashMap<>();
    data.put("error", message);

    ResponseDto responseDto = new ResponseDto();
    responseDto.setStatus(status);
    responseDto.setData(data);

    return Mono.just(ResponseEntity.status(status).body(responseDto));
  }
}
