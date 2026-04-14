package com.qvc.orderflow.User;

public class UsernameAlreadyRegisteredException extends RuntimeException {
    public UsernameAlreadyRegisteredException(String message) {
        super(message);
    }
    public UsernameAlreadyRegisteredException(){
    }
}
