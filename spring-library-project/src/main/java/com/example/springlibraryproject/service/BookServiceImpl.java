package com.example.springlibraryproject.service;

import com.example.springlibraryproject.dto.AuthorDto;
import com.example.springlibraryproject.dto.BookDto;
import com.example.springlibraryproject.dto.GenreDto;
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

}
