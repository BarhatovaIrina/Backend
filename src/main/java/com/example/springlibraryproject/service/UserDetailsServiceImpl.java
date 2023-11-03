package com.example.springlibraryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class UserDetailsServiceImpl {
    @Autowired
    private UserServiceImpl userService;


}
