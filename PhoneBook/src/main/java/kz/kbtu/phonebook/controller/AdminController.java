package kz.kbtu.phonebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.kbtu.phonebook.jwt.JwtTokenFilter;
import kz.kbtu.phonebook.models.UserRoles;
import kz.kbtu.phonebook.models.Users;
import kz.kbtu.phonebook.repo.RolesRepo;
import kz.kbtu.phonebook.repo.UsersRepo;
import kz.kbtu.phonebook.repo.UsersRolesRepo;
import kz.kbtu.phonebook.service.impls.CasbinServiceImpl;
import kz.kbtu.phonebook.service.impls.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.HashMap;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UsersRepo usersRepo;
    @Autowired private JwtTokenFilter jwtTokenFilter;
    @Autowired private CasbinServiceImpl casbinService;
    @Autowired private MessageProducer messageProducer;

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete User")
    public ResponseEntity<String> delete(@PathVariable Integer id, HttpServletRequest request) {
        HashMap <String, String> authority = jwtTokenFilter.getUserDetailsByHttpRequest(request);

        if(casbinService.checkAuthorize(authority.get("rule"), "user", "delete")){
            Users user = usersRepo.findById(id).stream().findFirst().orElse(null);
            if(user == null){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Not found it");
            }

            usersRepo.delete(user);
            messageProducer.sendMessage("my-topic", "User successfully deleted");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Successfully Deleted");
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("You don't have permission to delete user");
    }

}
