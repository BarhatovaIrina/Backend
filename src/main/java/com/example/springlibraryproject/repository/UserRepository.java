package com.example.springlibraryproject.repository;

import com.example.springlibraryproject.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

}
