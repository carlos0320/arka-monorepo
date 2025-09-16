package com.arka.inventorymcsv.exception;

import com.arka.inventorymcsv.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest) {
      return new ResponseEntity<>(new ErrorResponseDto(
              webRequest.getDescription(false),
              HttpStatus.NOT_FOUND,
              e.getMessage(),
              LocalDateTime.now()
      ), HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler(ResourceAlreadyExistsException.class)
   public ResponseEntity<ErrorResponseDto> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e, WebRequest webRequest) {
      return new ResponseEntity<>(new ErrorResponseDto(
              webRequest.getDescription(false),
              HttpStatus.BAD_REQUEST,
              e.getMessage(),
              LocalDateTime.now()
      ), HttpStatus.BAD_REQUEST);
   }
}
