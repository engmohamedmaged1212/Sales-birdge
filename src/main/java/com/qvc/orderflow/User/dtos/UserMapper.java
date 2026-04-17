package com.qvc.orderflow.User.dtos;

import com.qvc.orderflow.User.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
    User toUser(UserCreationRequestDto userCreationRequestDto);
}
