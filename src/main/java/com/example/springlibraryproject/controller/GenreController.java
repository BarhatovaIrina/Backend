package com.example.springlibraryproject.controller;

import com.example.springlibraryproject.dto.GenreDto;
import com.example.springlibraryproject.model.Author;
import com.example.springlibraryproject.service.BookService;
import com.example.springlibraryproject.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/genre/{id}")
    GenreDto getGenreById(@PathVariable("id") Long id) {
        return genreService.getGenreById(id);
    }

}
