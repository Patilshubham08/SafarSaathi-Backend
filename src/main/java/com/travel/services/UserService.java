package com.travel.services;

import com.travel.entities.User;

public interface UserService {
    User registerUser(User user);
    User loginUser(String email, String password);
}