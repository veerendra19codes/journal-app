package com.engineeringdigest.journal_app.controller;

import com.engineeringdigest.journal_app.entity.UserEntity;
import com.engineeringdigest.journal_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/create-user")
    private void saveUser(@RequestBody UserEntity userEntity) {
        userService.saveNewUser(userEntity);
    }
}
