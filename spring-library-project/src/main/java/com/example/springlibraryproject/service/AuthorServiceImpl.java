package com.example.springlibraryproject.service;

import com.example.springlibraryproject.dto.AuthorCreateDto;
import com.example.springlibraryproject.dto.AuthorDto;
import com.example.springlibraryproject.dto.AuthorUpdateDto;
import com.example.springlibraryproject.dto.BookDto;
import com.example.springlibraryproject.model.Author;
import com.example.springlibraryproject.model.Book;
import com.example.springlibraryproject.repository.AuthorRepository;
import com.example.springlibraryproject.repository.BookRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public AuthorDto getAuthorBySurnameV1(String surname) {
        Author author = authorRepository.findAuthorBySurname(surname).orElseThrow();
        return convertToDto(author);
    }

    @Override
    public AuthorDto getAuthorBySurnameV2(String surname){
        Author author = authorRepository.findAuthorBySurnameBySql(surname).orElseThrow();
        return convertToDto(author);
    }
@Override
public AuthorDto getAuthorBySurnameV3(String surname) {
    Specification<Author> specification = Specification.where(new Specification<Author>() {
        @Override
        public Predicate toPredicate(Root<Author> root,
                                     CriteriaQuery<?> query,
                                     CriteriaBuilder cb) {
            return cb.equal(root.get("surname"), surname);
        }
    });

    Author author = authorRepository.findOne(specification).orElseThrow();
    return convertToDto(author);
}
    @Override
    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        return convertToDto(author);
    }

    private AuthorDto convertToDto(Author author) {
        List<BookDto> bookDtoList = null;
       if(author.getBooks()!=null) {
            bookDtoList = author.getBooks()
                   .stream()
                   .map(book -> BookDto.builder()
                           .genre(book.getGenre().getName())
                           .name(book.getName())
                           .id(book.getId())
                           .build()
                   ).toList();
       }
        return AuthorDto.builder()
                .books(bookDtoList)
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }

    // **** CRUD **************
    //create
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto){
        Author author = authorRepository.save(convertDtoToEntity(authorCreateDto));
        AuthorDto authorDto = convertToDto(author);
        return authorDto;
    }

    //update
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto){
        Author author = authorRepository.findById(authorUpdateDto.getId()).orElseThrow();;
        author.setName(authorUpdateDto.getName());
        author.setSurname(authorUpdateDto.getSurname());
        Author updateAuthor = authorRepository.save(author);
        return convertToDto(updateAuthor);
    }

    //delete
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    private Author convertDtoToEntity(AuthorCreateDto authorCreateDto) {
        return Author.builder()
                .name(authorCreateDto.getName())
                .surname(authorCreateDto.getSurname())
                .build();
    }

    public List <AuthorDto> getAllAuthors(){
        List <Author> authors = authorRepository.findAll();
        return authors.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
