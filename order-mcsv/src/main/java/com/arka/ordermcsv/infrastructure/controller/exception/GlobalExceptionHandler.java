package com.arka.ordermcsv.infrastructure.controller.exception;

import com.arka.ordermcsv.domain.exception.DomainException;
import com.arka.ordermcsv.domain.exception.ValidationException;
import com.arka.ordermcsv.infrastructure.controller.exception.dto.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(DomainException.class)
  public ResponseEntity<ErrorDto> handleDomainException(DomainException ex, HttpServletRequest request){
    ErrorDto error = new ErrorDto(
            ex.getClass().getSimpleName(),
            ex.getMessage(),
            request.getRequestURI()
    );

    HttpStatus status = HttpStatus.BAD_REQUEST;
    if (ex.getClass().getSimpleName().contains("NotFound")) {
      status = HttpStatus.NOT_FOUND;
    }
    return ResponseEntity.status(status).body(error);
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorDto> handleValidationException(ValidationException ex, HttpServletRequest request){
    ErrorDto error = new ErrorDto(
            "VALIDATION_ERROR",
            ex.getMessage(),
            request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDto> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    String message = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .findFirst()
            .orElse("Invalid input");

    return ResponseEntity.badRequest().body(new ErrorDto(
            "VALIDATION_ERROR",
            message,
            req.getRequestURI()
    ));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleInternalServerErrors(Exception ex, HttpServletRequest request){
    ErrorDto error = new ErrorDto(
            "INTERNAL_SERVER_ERROR",
            ex.getMessage(),
            request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
