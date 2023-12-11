package kz.kbtu.phonebook.service.implementations;

import kz.kbtu.phonebook.dtos.AuthRequest;
import kz.kbtu.phonebook.dtos.AuthResponse;
import kz.kbtu.phonebook.dtos.UserCreatedResponse;
import kz.kbtu.phonebook.exceptions.CustomBadCredentialsException;
import kz.kbtu.phonebook.exceptions.UserNotFoundException;
import kz.kbtu.phonebook.jwt.JwtTokenUtil;
import kz.kbtu.phonebook.models.*;
import kz.kbtu.phonebook.repository.RoleRepository;
import kz.kbtu.phonebook.repository.UserRepository;
import kz.kbtu.phonebook.repository.UserRoleRepository;
import kz.kbtu.phonebook.service.interfaces.AuthService;
import kz.kbtu.phonebook.service.interfaces.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.net.URI;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    private final JwtTokenUtil jwtUtil;
    private final AuthenticationManager authManager;

    private final KafkaService kafkaService;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(), authRequest.getPassword())
        );

        if(!authentication.isAuthenticated()){
            throw new CustomBadCredentialsException("User was not Authenticated");
        }

        User user = (User) authentication.getPrincipal();
        String accessToken = jwtUtil.generateAccessToken(userRoleRepository.findUserRolesByUser(user));

        AuthResponse response = new AuthResponse(user.getEmail(), accessToken);
        kafkaService.sendMessage("my-topic", response.toString());

        return response;
    }

    @Override
    public UserCreatedResponse register(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new UserNotFoundException("User with this email already exists");
        }

        User savedUser = userRepository.save(new User(user));
        if(savedUser==null) {
            throw new CustomBadCredentialsException("User was not saved");
        }

        URI userURI = URI.create("/auth/register/" + savedUser.getId());

        userRoleRepository.save(new UserRoles(savedUser, roleRepository.findById(1).stream().findFirst().orElse(null)));
        kafkaService.sendMessage("my-topic", savedUser.toString());

        return new UserCreatedResponse(savedUser , userURI);
    }
    
}
