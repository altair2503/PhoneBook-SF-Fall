package kz.kbtu.phonebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.kbtu.phonebook.domain.User;
import kz.kbtu.phonebook.repository.UserRepository;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @Operation(summary = "Get all users")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Page<User> getAllUsers(@ParameterObject Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @GetMapping("/search")
    @Operation(summary = "Search user by name")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public List<User> getAllUsers(@RequestParam(value = "name") String name) {
        return (List<User>) userRepository.findByFirstNameOrLastName(name, name).stream().toList();
    }

}
