package com.travel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.entities.User;
import com.travel.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public User registerUser(User user) {
        // Check if email already exists
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }
        // Save the raw user (In real life, we encrypt passwords here)
        return userRepo.save(user);
    }

    @Override
    public User loginUser(String email, String password) {
        // Find user by email
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check password
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        
        return user;
    }
}