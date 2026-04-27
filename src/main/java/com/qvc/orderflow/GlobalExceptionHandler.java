package com.qvc.orderflow;

import com.qvc.orderflow.exceptions.UsernameAlreadyRegisteredException;
import com.qvc.orderflow.exceptions.ProductNotFoundException;
import com.qvc.orderflow.exceptions.VariantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> handleValidationErrors(
            MethodArgumentNotValidException exception
    ){
        var errors = new HashMap<String ,String>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField() , error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UsernameAlreadyRegisteredException.class)
    public ResponseEntity<Map<String , String>> handleEmailAlreadyRegistered(UsernameAlreadyRegisteredException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error" , ex.getMessage() ));
    }

    //ProductNotFoundException
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String , String>> handleEmailAlreadyRegistered(ProductNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error" , ex.getMessage() ));
    }

    //VariantNotFoundException
    @ExceptionHandler(VariantNotFoundException.class)
    public ResponseEntity<Map<String , String>> handleEmailAlreadyRegistered(VariantNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error" , ex.getMessage() ));
    }
}
