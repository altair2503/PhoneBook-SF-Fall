package kz.kbtu.phonebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.kbtu.phonebook.dtos.AuthRequest;
import kz.kbtu.phonebook.dtos.UserCreatedResponse;
import kz.kbtu.phonebook.models.*;
import kz.kbtu.phonebook.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization", description = "AuthorizationController")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "User Login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok().body(authService.login(request));
    }

    @PostMapping("/register")
    @Operation(summary = "User Registration")
    public ResponseEntity<User> register(@RequestBody @Valid User user) {
        UserCreatedResponse userCreatedResponse = authService.register(user);
        return ResponseEntity.created(userCreatedResponse.getUserURI()).body(userCreatedResponse.getUser());
    }

}
