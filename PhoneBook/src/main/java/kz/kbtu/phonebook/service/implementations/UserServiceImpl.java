package kz.kbtu.phonebook.service.implementations;

import kz.kbtu.phonebook.dtos.UserDto;
import kz.kbtu.phonebook.exceptions.CustomBadCredentialsException;
import kz.kbtu.phonebook.exceptions.UserNotFoundException;
import kz.kbtu.phonebook.jwt.JwtTokenFilter;
import kz.kbtu.phonebook.mapper.UserMapper;
import kz.kbtu.phonebook.models.User;
import kz.kbtu.phonebook.repository.UserRepository;
import kz.kbtu.phonebook.service.interfaces.CasbinService;
import kz.kbtu.phonebook.service.interfaces.KafkaService;
import kz.kbtu.phonebook.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenFilter jwtTokenFilter;
    private final CasbinService casbinService;
    private final KafkaService kafkaService;

    private final UserMapper userMapper;


    @Override
    public Page<?> getAllUsers(Pageable pageable, HttpServletRequest request) {
        HashMap<String, String> authority = jwtTokenFilter.getUserDetailsByHttpRequest(request);

        if(casbinService.checkAuthorize(authority.get("rule"), "user", "read")) {
            return authority.get("rule").equals("ROLE_ADMIN") ?
                    userRepository.findAll(pageable)
                    : userRepository.findAll(pageable).map(userMapper::mapToUserDto);
        }
        else throw new UserNotFoundException("Users not Found");
    }

    @Override
    public Optional<?> getAllUsersByNameOrPhone(String nameOrPhone, HttpServletRequest request) {
        HashMap<String, String> authority = jwtTokenFilter.getUserDetailsByHttpRequest(request);

        if(casbinService.checkAuthorize(authority.get("rule"), "search", "read")) {
            if(userRepository.findUsersByUsernameOrPhone(nameOrPhone, nameOrPhone).isPresent()) {
                return authority.get("rule").equals("ROLE_ADMIN") ?
                        userRepository.findUsersByUsernameOrPhone(nameOrPhone, nameOrPhone)
                        : userRepository.findUsersByUsernameOrPhone(nameOrPhone, nameOrPhone).map(userMapper::mapToUserDto);
            }
            else throw new UserNotFoundException("Users not Found");
        }
        else throw new CustomBadCredentialsException("Unauthorized user");
    }

    @Override
    public Boolean updateUser(Long id, User user, HttpServletRequest request) {
        HashMap <String, String> authority = jwtTokenFilter.getUserDetailsByHttpRequest(request);

        if(casbinService.checkAuthorize(authority.get("rule"), "user", "update")) {
            Optional<User> foundUser = userRepository.findById(id);

            if(foundUser.isPresent()) {
                User updatedUser = foundUser.get();
                if(user.getUsername() != null) updatedUser.setUsername(user.getUsername());
                if(user.getPassword() != null) updatedUser.setPassword(user.getPassword());
                if(user.getEmail() != null) updatedUser.setEmail(user.getEmail());
                if(user.getPhone() != null) updatedUser.setPhone(user.getPhone());
                userRepository.save(updatedUser);
                kafkaService.sendMessage("my-topic", updatedUser.toString());
                return true;
            }
            else throw new UserNotFoundException("User not found");
        }
        else {
            throw new CustomBadCredentialsException("UNAUTHORIZED user");
        }
    }
}
