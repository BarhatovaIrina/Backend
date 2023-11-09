package com.example.springlibraryproject.service;

import com.example.springlibraryproject.model.Users;

import java.util.List;

public interface UserService {
    Users getUserById (Long id);
    List<Users> getAllUser();
}
