package com.example.springlibraryproject.service;

import com.example.springlibraryproject.dto.AuthorCreateDto;
import com.example.springlibraryproject.dto.AuthorDto;
import com.example.springlibraryproject.dto.AuthorUpdateDto;
import com.example.springlibraryproject.dto.BookDto;
import com.example.springlibraryproject.model.Author;

import java.util.List;

public interface AuthorService {
    List <AuthorDto> getAllAuthors();

    AuthorDto getAuthorById(Long id);
    List<AuthorDto> getAuthorByBookId(Long bookId);

    /// ****** варианты написания запроса по фамилии
    AuthorDto getAuthorBySurnameV1(String surname);
    AuthorDto getAuthorBySurnameV2(String surname);
    AuthorDto getAuthorBySurnameV3(String surname);

    //******** CRUD operations ******************
    // save
    AuthorDto createAuthor(AuthorCreateDto authorCreateDto);

    //update
    AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto);

    //delete
    void deleteAuthorById(Long id);
}
