package kz.kbtu.phonebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kz.kbtu.phonebook.jwt.JwtTokenUtil;
import kz.kbtu.phonebook.models.*;
import kz.kbtu.phonebook.repo.RolesRepo;
import kz.kbtu.phonebook.repo.UsersRepo;
import kz.kbtu.phonebook.repo.UsersRolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired AuthenticationManager authManager;
    @Autowired JwtTokenUtil jwtUtil;
    @Autowired UsersRepo userRepository;
    @Autowired UsersRolesRepo usersRolesRepo;
    @Autowired RolesRepo rolesRepo;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            Users user = (Users) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(usersRolesRepo.findUserRolesByUser(user));

            AuthResponse response = new AuthResponse(user.getEmail(), accessToken);

            return ResponseEntity.ok().body(response);

        } catch(BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    @Operation(summary = "User Registration")
    public ResponseEntity<Users> register(@RequestBody @Valid Users user) {
        Users savedUser = userRepository.save(new Users(user));
        URI userURI = URI.create("/auth/register/" + savedUser.getId());

        usersRolesRepo.save(new UserRoles(savedUser, rolesRepo.findById(1).stream().findFirst().orElse(null)));

        return ResponseEntity.created(userURI).body(savedUser);
    }

}
