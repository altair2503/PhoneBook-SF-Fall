package kz.kbtu.phonebook.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AdminController {


    @GetMapping("/users")
    public String checkPermission() {
       return null;
    }

}
