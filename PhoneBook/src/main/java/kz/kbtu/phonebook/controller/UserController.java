package kz.kbtu.phonebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.kbtu.phonebook.domain.User;
import kz.kbtu.phonebook.repository.UserRepository;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Operation(summary = "Create user")
    public ResponseEntity<User> create(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI userURI = URI.create("/users/" + savedUser.getId());

        return ResponseEntity.created(userURI).body(savedUser);
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public Page<User> getAllUsers(@ParameterObject Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
