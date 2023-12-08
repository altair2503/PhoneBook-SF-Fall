package kz.kbtu.phonebook.controller;

import lombok.RequiredArgsConstructor;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")

public class AdminController {
    @Autowired
    private Enforcer enforcer;

    @GetMapping("/users")
    public String checkPermission() {
        String sub = "alice";
        String obj = "data1";
        String act = "read";

        boolean authorized = enforcer.enforce(sub, obj, act);
        if (authorized) {
            return "Permission granted!";
        } else {
            return "Permission denied!";
        }
    }
//    @PostMapping
//    @Operation(summary = "Create User")
//    @RolesAllowed({"ROLE_ADMIN"})
//    public ResponseEntity<User> create(@RequestBody User user) {
//        User savedUser = userRepository.save(new User(user));
//        URI userURI = URI.create("/admin/" + savedUser.getId());
//
//        return ResponseEntity.created(userURI).body(savedUser);
//    }
//
//    @DeleteMapping
//    @Operation(summary = "Delete User")
//    @RolesAllowed({"ROLE_ADMIN"})
//    public ResponseEntity<String> delete(@RequestParam(value = "id") Integer id) {
//        User user = userRepository.findUserById(id).stream().findFirst().orElse(null);
//        userRepository.delete(user);
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body("Successfully Deleted");
//    }

}
