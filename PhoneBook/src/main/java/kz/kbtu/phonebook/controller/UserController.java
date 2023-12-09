package kz.kbtu.phonebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kbtu.phonebook.jwt.JwtTokenFilter;
import kz.kbtu.phonebook.models.Users;
import kz.kbtu.phonebook.repo.UsersRepo;
import kz.kbtu.phonebook.service.impls.CasbinServiceImpl;
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

@AllArgsConstructor
@RestController
@Tag(name = "User", description = "UserController")
@RequestMapping("/users")
public class UserController {

    @Autowired private UsersRepo usersRepo;
    @Autowired private JwtTokenFilter jwtTokenFilter;
    @Autowired private CasbinServiceImpl casbinService;

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
}
