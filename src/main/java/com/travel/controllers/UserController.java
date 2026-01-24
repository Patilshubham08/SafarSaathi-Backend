package com.travel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.entities.User;
import com.travel.dtos.LoginDto; // 1. Import your DTO
import com.travel.services.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Login (Updated to use LoginDto)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDetails) { // 2. Use LoginDto here
        try {
            // 3. Extract email/pass from the DTO
            User user = userService.loginUser(loginDetails.getEmail(), loginDetails.getPassword());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}