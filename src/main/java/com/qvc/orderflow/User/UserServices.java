package com.qvc.orderflow.User;

import com.qvc.orderflow.User.dtos.ChangePasswordDto;
import com.qvc.orderflow.User.dtos.UserCreationRequestDto;
import com.qvc.orderflow.User.dtos.UserDto;
import com.qvc.orderflow.User.dtos.UserMapper;
import com.qvc.orderflow.exceptions.UsernameAlreadyRegisteredException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServices implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto createUser(UserCreationRequestDto userCreationRequestDto) {
        if (userRepository.findByUsername(userCreationRequestDto.getUsername()).isPresent()) {
            throw  new UsernameAlreadyRegisteredException("this username is already in use");
        }
        User user = new User();
        user.setUsername(userCreationRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(userCreationRequestDto.getPassword()));
        user.setRole(Role.valueOf(userCreationRequestDto.getRole()));
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setId(user.getId());
        userDto.setRole(user.getRole().toString());
        return userDto;
    }
    // in dev mode only
    UserDto changePassword(ChangePasswordDto requestDto) {
        var user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("username not found"));
        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        var user_= userRepository.save(user);
        return userMapper.toUserDto(user_);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}
