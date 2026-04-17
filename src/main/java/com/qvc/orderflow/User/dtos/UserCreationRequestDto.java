package com.qvc.orderflow.User.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreationRequestDto {
    @NotBlank(message = "Name is required")
    @Size(max = 255 , message = "Name must be less than 255")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6 ,max = 25, message = "Password must be between 6 and 25 characters")
    private String password;

    @NotBlank(message = "Admin is required")
    private String role;
}
