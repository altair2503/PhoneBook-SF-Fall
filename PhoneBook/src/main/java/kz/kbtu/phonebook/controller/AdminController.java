package kz.kbtu.phonebook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kbtu.phonebook.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin", description = "AdminController")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete User")
    public ResponseEntity<String> delete(@PathVariable Long id, HttpServletRequest request) {
        adminService.delete(id,request);
        return ResponseEntity.ok().body("User was successfully deleted");
    }

}
