package com.example.springlibraryproject.service;

import com.example.springlibraryproject.dto.*;
import com.example.springlibraryproject.model.Author;
import com.example.springlibraryproject.model.Book;
import com.example.springlibraryproject.model.Genre;
import com.example.springlibraryproject.repository.AuthorRepository;
import com.example.springlibraryproject.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public BookDto getBookByGenre(Long id) {
        Book book = bookRepository.findById(id).orElseThrow();//() -> new NoSuchElementException("Book not found for genre " + genre.getName()));
        return convertToDto(book);
    }

    @Override
    public List <BookDto> getBooks(List <Book> books) {
        return convertToDto(books);
    }
    private BookDto convertToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre().getName())
                .build();
    }
    private List <BookDto> convertToDto(List <Book> books){
        List<BookDto> bookDtoList = books.stream()
                .map(book -> BookDto.builder()
                                .genre(book.getGenre().getName())
                                .name(book.getName())
                                .id(book.getId())
                                .build()
                ).toList();
        return  bookDtoList;
    }

    //******** CRUD operations ******************
    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow();
        return convertToDto(book);
    }

    // create
    @Override
    public BookDto createBook(BookCreateDto bookCreateDto)
    {
        Book book = bookRepository.save(convertDtoToEntity(bookCreateDto));
        return convertToDto(book);
    }

    //update
    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        Book book = bookRepository.findById(bookUpdateDto.getId()).orElseThrow();
        book.setName(bookUpdateDto.getName());
        book.setGenre(bookUpdateDto.getGenre());
        Book updateBook = bookRepository.save(book);
        return  convertToDto(updateBook);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
    @Override
    public List <BookDto> getAllBooks(){
        List <Book> books = bookRepository.findAll();
        return convertToDto(books);
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto) {
        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(bookCreateDto.getGenre())
                .build();
    }
}
