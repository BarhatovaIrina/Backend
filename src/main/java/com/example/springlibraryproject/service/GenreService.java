package com.example.springlibraryproject.service;

import com.example.springlibraryproject.dto.*;

import java.util.List;

public interface GenreService {
    GenreDto getGenreById(Long id);

    GenreDto createGenre(GenreCreateDto genreCreateDto);

    GenreDto updateGenre (GenreUpdateDto genreUpdateDto);

    //  void deleteGenreById(Long id);
    List<GenreDto> getAllGenres();

}
