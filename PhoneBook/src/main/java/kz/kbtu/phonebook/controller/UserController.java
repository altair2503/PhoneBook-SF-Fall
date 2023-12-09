package kz.kbtu.phonebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kbtu.phonebook.jwt.JwtTokenFilter;
import kz.kbtu.phonebook.models.Users;
import kz.kbtu.phonebook.repo.UsersRepo;
import kz.kbtu.phonebook.service.impls.CasbinServiceImpl;
import kz.kbtu.phonebook.service.impls.MessageProducer;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Optional;

@AllArgsConstructor
@RestController
@Tag(name = "User", description = "UserController")
@RequestMapping("/users")
public class UserController {

    @Autowired private UsersRepo usersRepo;
    @Autowired private JwtTokenFilter jwtTokenFilter;
    @Autowired private CasbinServiceImpl casbinService;
    @Autowired private MessageProducer messageProducer;

    @GetMapping
    @Operation(summary = "Get all users")
    public Page<Users> getAllUsers(@ParameterObject Pageable pageable, HttpServletRequest request) {
        HashMap<String, String> authority = jwtTokenFilter.getUserDetailsByHttpRequest(request);
        if(casbinService.checkAuthorize(authority.get("rule"), "users", "read")){
            return usersRepo.findAll(pageable);
        }
        return Page.empty();
    }

    @GetMapping("/search/{nameOrPhone}")
    @Operation(summary = "Search user by name")
    public ResponseEntity<?> getAllUsers(@PathVariable String nameOrPhone, HttpServletRequest request) {
        HashMap<String, String> authority = jwtTokenFilter.getUserDetailsByHttpRequest(request);
        if(casbinService.checkAuthorize(authority.get("rule"), "search", "read")){
            if(usersRepo.findUsersByUsernameOrPhone(nameOrPhone, nameOrPhone).isPresent()) {
                return ResponseEntity.ok(usersRepo.findUsersByUsernameOrPhone(nameOrPhone, nameOrPhone));
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Not found any matches");
            }

        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Access denied");
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update user data")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody Users user, HttpServletRequest request) {
        HashMap <String, String> authority = jwtTokenFilter.getUserDetailsByHttpRequest(request);

        if(casbinService.checkAuthorize(authority.get("rule"), "user", "update")) {
            Optional<Users> foundUser = usersRepo.findById(id);
            System.out.println(foundUser);

            if(foundUser.isPresent()){
                Users updatedUser = foundUser.get();
                if(user.getUsername() != null) updatedUser.setUsername(user.getUsername());
                if(user.getPassword() != null) updatedUser.setPassword(user.getPassword());
                if(user.getEmail() != null) updatedUser.setEmail(user.getEmail());
                if(user.getPhone() != null) updatedUser.setPhone(user.getPhone());

                messageProducer.sendMessage("my-topic", updatedUser.toString());
                return new ResponseEntity<>(usersRepo.save(updatedUser), HttpStatus.OK);
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Not found it");
            }
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Access denied");
    }

}
