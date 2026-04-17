package com.qvc.orderflow.User.dtos;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String username;
    private String newPassword;
}
