package com.example.springlibraryproject.service;

import com.example.springlibraryproject.dto.AuthorDto;
import com.example.springlibraryproject.dto.BookDto;
import com.example.springlibraryproject.model.Author;
import com.example.springlibraryproject.model.Book;
import com.example.springlibraryproject.model.Genre;

import java.util.List;

public interface BookService {
    BookDto getBookByGenre(Long id);
    List <BookDto> getBooks(List <Book> books);

}
