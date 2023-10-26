package com.example.springlibraryproject.service;

import com.example.springlibraryproject.dto.AuthorDto;
import com.example.springlibraryproject.model.Author;

import java.util.List;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);

   List<AuthorDto> getAuthorByBookId(Long bookId);
}
