package com.example.springlibraryproject.service;

import com.example.springlibraryproject.dto.*;
import com.example.springlibraryproject.model.Author;
import com.example.springlibraryproject.model.Book;
import com.example.springlibraryproject.model.Genre;

import java.util.List;

public interface BookService {
    BookDto getBookByGenre(Long id);
    List <BookDto> getBooks(List <Book> books);

    BookDto getBookById(Long id);
    //******** CRUD operations ******************
    // create
    BookDto createBook(BookCreateDto bookCreateDto);

    //update
    BookDto updateBook (BookUpdateDto bookUpdateDto);

    //delete
    void deleteBookById(Long id);

    //select all
    List <BookDto> getAllBooks();
}
