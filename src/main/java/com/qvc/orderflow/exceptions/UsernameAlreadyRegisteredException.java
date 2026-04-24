package com.qvc.orderflow.exceptions;

public class UsernameAlreadyRegisteredException extends RuntimeException {
    public UsernameAlreadyRegisteredException(String message) {
        super(message);
    }
    public UsernameAlreadyRegisteredException(){
    }
}
