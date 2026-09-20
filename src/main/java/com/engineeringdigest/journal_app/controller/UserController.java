package com.engineeringdigest.journal_app.controller;

import com.engineeringdigest.journal_app.entity.UserEntity;
import com.engineeringdigest.journal_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    private List<UserEntity> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping()
    private void saveUser(@RequestBody UserEntity userEntity) {
        userService.saveEntry(userEntity);
    }

    @PutMapping("{userName}")
    private UserEntity updateUser(@RequestBody UserEntity userEntity, @PathVariable String userName) {
        UserEntity oldUser = userService.getByUserName(userName);
        if(oldUser != null) {
            oldUser.setUserName(userEntity.getUserName());
            oldUser.setPassword(userEntity.getPassword());
            userService.saveEntry(oldUser);
        }
        return oldUser;
    }

}
