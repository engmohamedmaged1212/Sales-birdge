package com.qvc.orderflow.User.Authentication;

import com.qvc.orderflow.User.User;
import com.qvc.orderflow.User.UserRepository;
import com.qvc.orderflow.User.dtos.LoginDto;
import com.qvc.orderflow.jwt.JwtConfig;
import com.qvc.orderflow.jwt.JwtResponse;
import com.qvc.orderflow.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody @Valid LoginDto loginRequestDto,
            HttpServletResponse response){

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );
        var user = (User)auth.getPrincipal();
        var accessToken = jwtService.generateAccessTokens(user);
        var refreshToken = jwtService.generateRefreshTokens(user);

        var cookie = new Cookie("refreshToken" , refreshToken.toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth");
        cookie.setMaxAge((int)jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);

        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue(value = "refreshToken") String refreshToken){
        var jwt = jwtService.parseToken(refreshToken);
        if(!jwt.isValid()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var userId = jwt.getUserId();
        var user = userRepository.findById(userId).orElseThrow();
        var accessToken =jwtService.generateAccessTokens(user);
        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }

}
