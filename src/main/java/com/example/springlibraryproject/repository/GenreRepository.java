package com.example.springlibraryproject.repository;

import com.example.springlibraryproject.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
