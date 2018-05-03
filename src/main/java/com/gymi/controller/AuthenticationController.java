package com.gymi.controller;

import com.gymi.model.User;
import com.gymi.repository.UserRepository;
import com.gymi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;


@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public HttpStatus createUser(@Valid @RequestBody User user) {
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());

        try {
            userRepository.save(user);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.UNAUTHORIZED;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@PathVariable(value = "token") Long token) {
        //TODO
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getuser/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @PutMapping("/notes/{id}")
    public User updateUser(@PathVariable(value = "id") Long noteId,
                           @Valid @RequestBody User userDetails) {

        User user = userRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", noteId));

        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long noteId) {
        User user = userRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", noteId));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
