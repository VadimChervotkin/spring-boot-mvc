package org.example.springbootmvc.users;

import org.example.springbootmvc.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public User toUser(UserDto userDto) {
        return new User(
                userDto.id(),
                userDto.name(),
                userDto.email(),
                userDto.age(),
                userDto.pets()
        );
    }

    public UserDto toDto(User user) {
        return new UserDto(
                user.id(),
                user.name(),
                user.email(),
                user.age(),
                user.pets()
        );
    }
}
