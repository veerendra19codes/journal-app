package com.engineeringdigest.journal_app.controller;

import com.engineeringdigest.journal_app.entity.UserEntity;
import com.engineeringdigest.journal_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping()
    private UserEntity updateUser(@RequestBody UserEntity userEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntity oldUser = userService.getByUserName(userName);
        if(oldUser != null) {
            oldUser.setUserName(userEntity.getUserName());
            oldUser.setPassword(userEntity.getPassword());
            userService.saveNewUser(oldUser);
        }
        return oldUser;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userService.deleteByUsername(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
