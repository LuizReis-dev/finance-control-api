package com.fc.financecontrolapi.controllers.exception;

import com.fc.financecontrolapi.dtos.errors.CustomError;
import com.fc.financecontrolapi.dtos.errors.ValidationError;
import com.fc.financecontrolapi.exceptions.UnprocessableEntityException;
import com.fc.financecontrolapi.exceptions.user.AuthenticationException;
import com.fc.financecontrolapi.exceptions.user.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<CustomError> userAlreadyExists(UserAlreadyExistsException e, HttpServletRequest request){

        CustomError response = new CustomError(Instant.now(), 400, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError(Instant.now(), status.value(),"Invalid data",request.getRequestURI());
        for(FieldError f : e.getBindingResult().getFieldErrors()){
            err.addError(f.getField(),f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<CustomError> unprocessableEntity(UnprocessableEntityException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError(Instant.now(), status.value(),"Invalid data",request.getRequestURI());
        err.addError(e.getField(), e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomError> authenticationException(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.FORBIDDEN;
        CustomError response = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
