package com.example.springlibraryproject.service;

import com.example.springlibraryproject.dto.*;
import com.example.springlibraryproject.model.Author;
import com.example.springlibraryproject.model.Book;
import com.example.springlibraryproject.model.Genre;
import com.example.springlibraryproject.repository.AuthorRepository;
import com.example.springlibraryproject.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
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
    // select (get)
    @Override
    public BookDto getBookById(Long id) {
        log.info("Try to find book by id {}", id);
        Optional <Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            log.info("Book: {}", book.toString());
            return convertToDto(book.get());
        }
        else {
            log.error("Book by id {} not found", id);
            throw new NoSuchElementException();
        }
    }

    @Override
    public List <BookDto> getAllBooks(){
        log.info("Try to find all books");
        List <Book> books = bookRepository.findAll();
        if (!books.isEmpty()) {
            log.info("Found {} book(s)", books.size());
            return convertToDto(books);
        } else {
            log.info("No books found");
            return new ArrayList<>();
        }
    }

    // create
    @Override
    public BookDto createBook(BookCreateDto bookCreateDto)
    {
        log.info("Try to create book with data {}", bookCreateDto.toString());
        Book book = bookRepository.save(convertDtoToEntity(bookCreateDto));
        if (book.getId() != null) {
            log.info("Book created with id {}", book.getId());
            return convertToDto(book);
        } else {
            log.error("Failed to create book");
            throw new RuntimeException("Failed to create book");
        }
    }

    //update
    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        log.info("Try to update book with data {}", bookUpdateDto.toString());
        Optional <Book> optionalBook = bookRepository.findById(bookUpdateDto.getId());
        if (optionalBook.isPresent())
        {
            Book book = optionalBook.get();
            book.setName(bookUpdateDto.getName());
            book.setGenre(bookUpdateDto.getGenre());
            Book updatedBook = bookRepository.save(book);
            log.info("Book updated: {}", updatedBook.toString());
            return  convertToDto(updatedBook);
        }
        else {
            log.error("Book by id {} not found", bookUpdateDto.getId());
            throw new NoSuchElementException();
        }

    }

    @Override
    public void deleteBookById(Long id) {
        log.info("Try to delete book by id {}", id);
        bookRepository.deleteById(id);
        Optional<Book> deletedBook = bookRepository.findById(id);
        if (deletedBook.isPresent()) {
            log.error("Failed to delete author by id {}", id);
            throw new RuntimeException("Failed to delete book by id " + id);
        } else {
            log.info("Book by id {} successfully deleted", id);
        }
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto) {
        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(bookCreateDto.getGenre())
                .build();
    }
}
