package kz.kbtu.phonebook.service.impls;

import kz.kbtu.phonebook.dtos.AuthRequest;
import kz.kbtu.phonebook.dtos.AuthResponse;
import kz.kbtu.phonebook.dtos.UserCreatedResponce;
import kz.kbtu.phonebook.exceptions.CustomBadCredentialsException;
import kz.kbtu.phonebook.exceptions.UserNotFoundException;
import kz.kbtu.phonebook.jwt.JwtTokenUtil;
import kz.kbtu.phonebook.models.*;
import kz.kbtu.phonebook.repo.RolesRepo;
import kz.kbtu.phonebook.repo.UsersRepo;
import kz.kbtu.phonebook.repo.UsersRolesRepo;
import kz.kbtu.phonebook.service.interfaces.AuthService;
import kz.kbtu.phonebook.service.interfaces.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtUtil;
    private final UsersRepo userRepository;
    private final UsersRolesRepo usersRolesRepo;
    private final RolesRepo rolesRepo;
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
        Users user = (Users) authentication.getPrincipal();
        String accessToken = jwtUtil.generateAccessToken(usersRolesRepo.findUserRolesByUser(user));
        AuthResponse response = new AuthResponse(user.getEmail(), accessToken);
        kafkaService.sendMessage("my-topic", response.toString());
        return response;
    }

    @Override
    public UserCreatedResponce register(Users users) {
        if(userRepository.findByEmail(users.getEmail()).isPresent()){
            throw new UserNotFoundException("User with this email already exists");
        }
        Users savedUser = userRepository.save(users);
        if(savedUser==null){
            throw new CustomBadCredentialsException("User was not saved");
        }
        URI userURI = URI.create("/auth/register/" + savedUser.getId());
        usersRolesRepo.save(new UserRoles(savedUser, rolesRepo.findById(1).stream().findFirst().orElse(null)));
        kafkaService.sendMessage("my-topic", savedUser.toString());
        return new UserCreatedResponce(savedUser , userURI);
    }
}
