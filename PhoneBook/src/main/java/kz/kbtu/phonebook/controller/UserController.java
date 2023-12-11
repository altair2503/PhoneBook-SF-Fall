package kz.kbtu.phonebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kbtu.phonebook.dtos.UserDto;
import kz.kbtu.phonebook.models.User;
import kz.kbtu.phonebook.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@Tag(name = "User", description = "UserController")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @Operation(summary = "Get all users")
    public Page<?> getAllUsers(@ParameterObject Pageable pageable, HttpServletRequest request) {
        return userService.getAllUsers(pageable, request);
    }

    @GetMapping("/search/{nameOrPhone}")
    @Operation(summary = "Search user by name")
    public ResponseEntity<?> searchUser(@PathVariable String nameOrPhone, HttpServletRequest request) {
        return ResponseEntity.ok(userService.getAllUsersByNameOrPhone(nameOrPhone, request));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update user data")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user, HttpServletRequest request) {
        if(userService.updateUser(id , user , request)){
            return ResponseEntity.ok().body("User was updated");
        }
        return ResponseEntity.ok().body("User was not updated");
    }

}
