package com.example.springlibraryproject.service;

import com.example.springlibraryproject.dto.AuthorDto;
import com.example.springlibraryproject.dto.BookDto;
import com.example.springlibraryproject.model.Author;
import com.example.springlibraryproject.model.Book;
import com.example.springlibraryproject.repository.AuthorRepository;
import com.example.springlibraryproject.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService{
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookService bookService;

    @Override
    public List <AuthorDto> getAuthorByBookId(Long bookId){
        Book book = bookRepository.findById(bookId).orElseThrow();

        List <Author> authors = book.getAuthors().stream().toList();
        List<AuthorDto> authorsDto = new ArrayList<>();

        for (Author author : authors) {
            AuthorDto authorDto = new AuthorDto();
            authorDto.setId(author.getId());
            authorDto.setName(author.getName());
            authorDto.setSurname(author.getSurname());
            authorDto.setBooks(bookService.getBooks(author.getBooks().stream().toList()));
            authorsDto.add(authorDto);
        }
        return authorsDto;
    }

    @Override
    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        return convertToDto(author);
    }

    private AuthorDto convertToDto(Author author) {
        List<BookDto> bookDtoList = author.getBooks()
                .stream()
                .map(book -> BookDto.builder()
                        .genre(book.getGenre().getName())
                        .name(book.getName())
                        .id(book.getId())
                        .build()
                ).toList();
        return AuthorDto.builder()
                .books(bookDtoList)
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }
}
