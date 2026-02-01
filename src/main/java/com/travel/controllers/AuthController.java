package com.travel.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.dtos.LoginDto;
import com.travel.dtos.RegisterDto;
import com.travel.entities.User;
import com.travel.entities.UserRole;
import com.travel.services.UserService;
import com.travel.utils.JwtUtils;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    // ===================== LOGIN =====================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            System.out.println("LOGIN REQUEST = " + loginDto);

            User user = userService.loginUser(
                    loginDto.getEmail(),
                    loginDto.getPassword()
            );

            String role = user.getUserRole().name();
            String token = jwtUtils.generateToken(user.getEmail(), role);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userRole", role);
            response.put("name", user.getName());
            response.put("userId", user.getUserId());
            response.put("email", user.getEmail());

            System.out.println("LOGIN SUCCESS FOR = " + user.getEmail());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }

    // ===================== REGISTER =====================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto dto) {

        // 🔴 MOST IMPORTANT DEBUG LINE
        System.out.println("RAW DTO OBJECT = " + dto);

        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body("Password is required");
        }

        if (dto.getName() == null || dto.getName().isBlank()) {
            return ResponseEntity.badRequest().body("Name is required");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        // Default role = CUSTOMER
        String role = dto.getUserRole();
        if (role == null || role.isBlank()) {
            role = "CUSTOMER";
        }

        user.setUserRole(UserRole.valueOf(role.toUpperCase()));

        userService.registerUser(user);

        System.out.println("USER REGISTERED = " + user.getEmail());

        return ResponseEntity.ok("User registered successfully");
    }
}
