package com.gymi.controller;

import com.gymi.model.User;
import com.gymi.service.AuthService;
import com.gymi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    UserController() {

    }

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@RequestHeader("Authorization") String authToken, @PathVariable("id") long id) {
        if (authService.isAuthenticated(authToken) != null) return authService.isAuthenticated(authToken);
        else {
            User user = userService.getUserById(id);
            if(user != null) {
                return new ResponseEntity(user, HttpStatus.OK);
            }
            else return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity getUserByUsername(@RequestHeader("Authorization") String authToken, @PathVariable("username") String username) {
        if (authService.isAuthenticated(authToken) != null) return authService.isAuthenticated(authToken);
        else {
            User user = userService.getUserByUsername(username);
            if(user != null) {
                return new ResponseEntity(user, HttpStatus.OK);
            }
            else return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}