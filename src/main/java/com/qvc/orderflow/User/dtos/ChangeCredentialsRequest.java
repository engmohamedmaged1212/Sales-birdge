package com.qvc.orderflow.User.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeCredentialsRequest {
    private String username;
    @NotBlank(message = "Role is required")
    private String role ;
    @NotNull(message = "please write the boolean")
    private Boolean isActive;
}
