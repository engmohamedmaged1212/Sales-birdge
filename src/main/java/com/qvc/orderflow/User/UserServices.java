package com.qvc.orderflow.User;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}
