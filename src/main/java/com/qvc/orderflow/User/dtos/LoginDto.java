package com.qvc.orderflow.User.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto{
    @NotBlank(message = "Username is req")
    private String username;
    @NotBlank(message = "Password is req")
    private String password;
}
