package com.example.springlibraryproject.controller;

import com.example.springlibraryproject.dto.*;
import com.example.springlibraryproject.model.Author;
import com.example.springlibraryproject.service.BookService;
import com.example.springlibraryproject.service.GenreService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "library")
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/genre")
    List< GenreDto> getAllGenres (){
        return genreService.getAllGenres();
    }


    @GetMapping("/genre/{id}")
    GenreDto getGenreById(@PathVariable("id") Long id) {
        return genreService.getGenreById(id);
    }


    @PostMapping("/genre/create")
    GenreDto createBook(@RequestBody @Valid GenreCreateDto genreCreateDto) {
        return genreService.createGenre(genreCreateDto);
    }

    @PutMapping ("/genre/update")
    GenreDto updateBook(@RequestBody @Valid GenreUpdateDto genreUpdateDto) {
        return genreService.updateGenre(genreUpdateDto);
    }

//    @DeleteMapping("genre/delete/{id}")
//    public void deleteGenreById(@PathVariable ("id") Long id){
//        genreService.deleteGenrById(id);
//    }
}
