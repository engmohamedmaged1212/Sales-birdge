package com.qvc.orderflow.User;

import com.qvc.orderflow.User.dtos.UserCreationRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserServices userServices;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreationRequestDto userCreationRequestDto) {
        var u = userServices.createUser(userCreationRequestDto);
        return ResponseEntity.ok(u);
    }
}
