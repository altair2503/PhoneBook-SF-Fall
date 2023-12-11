package kz.kbtu.phonebook.mapper;

import kz.kbtu.phonebook.dtos.UserDto;
import kz.kbtu.phonebook.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        return userDto;
    }

}
