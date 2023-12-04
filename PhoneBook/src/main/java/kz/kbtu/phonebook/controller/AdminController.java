package kz.kbtu.phonebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.kbtu.phonebook.domain.User;
import kz.kbtu.phonebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.URI;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Operation(summary = "Create User")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<User> create(@RequestBody User user) {
        User savedUser = userRepository.save(new User(user));
        URI userURI = URI.create("/admin/" + savedUser.getId());

        return ResponseEntity.created(userURI).body(savedUser);
    }

    @DeleteMapping
    @Operation(summary = "Delete User")
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<String> delete(@RequestParam(value = "id") Integer id) {
        User user = userRepository.findUserById(id).stream().findFirst().orElse(null);
        userRepository.delete(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Successfully Deleted");
    }

}
