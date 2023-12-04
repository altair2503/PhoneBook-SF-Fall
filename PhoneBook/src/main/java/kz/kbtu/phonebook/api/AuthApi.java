package kz.kbtu.phonebook.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kz.kbtu.phonebook.domain.User;
import kz.kbtu.phonebook.jwt.JwtTokenUtil;
import kz.kbtu.phonebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
public class AuthApi {

    @Autowired AuthenticationManager authManager;
    @Autowired JwtTokenUtil jwtUtil;

    @Autowired private UserRepository userRepository;

    @PostMapping("/auth/login")
    @Operation(summary = "User Authorization")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();
            System.out.println(user);
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getEmail(), accessToken);

            return ResponseEntity.ok().body(response);

        } catch(BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/auth/register")
    @Operation(summary = "User Registration")
    public ResponseEntity<User> register(@RequestBody @Valid User user) {
        User savedUser = userRepository.save(new User(user));
        URI userURI = URI.create("/auth/register/" + savedUser.getId());

        return ResponseEntity.created(userURI).body(savedUser);
    }

}
