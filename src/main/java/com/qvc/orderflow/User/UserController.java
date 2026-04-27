package com.qvc.orderflow.User;

import com.qvc.orderflow.User.dtos.ChangeCredentialsRequest;
import com.qvc.orderflow.User.dtos.ChangePasswordDto;
import com.qvc.orderflow.User.dtos.UserCreationRequestDto;
import com.qvc.orderflow.User.dtos.UserDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserServices userServices;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreationRequestDto userCreationRequestDto) {
        var u = userServices.createUser(userCreationRequestDto);
        return ResponseEntity.ok(u);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("change-password")
    public ResponseEntity<UserDto> changePassword(@Valid @RequestBody ChangePasswordDto requestDto) {
        var response  = userServices.changePassword(requestDto);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("change-credentials")
    public ResponseEntity<?> changeCredentials(@Valid @RequestBody ChangeCredentialsRequest requestDto) {
        var response  = userServices.changeCredentials(requestDto);
        return ResponseEntity.ok(response);
    }


}
