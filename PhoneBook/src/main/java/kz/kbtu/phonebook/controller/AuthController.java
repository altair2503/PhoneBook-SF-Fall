package kz.kbtu.phonebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kz.kbtu.phonebook.dtos.AuthRequest;
import kz.kbtu.phonebook.dtos.UserCreatedResponce;
import kz.kbtu.phonebook.models.*;
import kz.kbtu.phonebook.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok().body(authService.login(request));
    }
    @PostMapping("/register")
    @Operation(summary = "User Registration")
    public ResponseEntity<Users> register(@RequestBody @Valid Users user) {
        UserCreatedResponce userCreatedResponce = authService.register(user);
        return ResponseEntity.created(userCreatedResponce.getUserURI()).body(userCreatedResponce.getUser());
    }
}
