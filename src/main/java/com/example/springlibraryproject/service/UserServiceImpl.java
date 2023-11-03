package com.example.springlibraryproject.service;

import com.example.springlibraryproject.dto.UserDto;
import com.example.springlibraryproject.model.Users;
import com.example.springlibraryproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private  final UserRepository userRepository;

    @Override
    public Users getUserById(Long id) {
        Users user = userRepository.findById(id).orElseThrow();
        return user;
    }
    private UserDto convertToDto(Users user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getUsername())
                .role(user.getRole().getName())
        .build();
    }

    public List<Users> getAllUser(){
        List <Users> users = userRepository.findAll();
        return  users;
    }
}
