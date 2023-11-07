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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService{
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookService bookService;

    @Override
    public List <AuthorDto> getAuthorByBookId(Long bookId){
        log.info("Try to find authors of book by id {}", bookId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            log.info("Book by id {} is found", book.getId());
            log.info("Now try to find authors for book's id {}", book.getId());
            List<Author> authors = book.getAuthors().stream().toList();
            if (authors.isEmpty()){
                log.info("Authors by book's id {} not found", book.getId());
                return new ArrayList<>();
            }
            else {
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
        }
        else {
            log.error("Book by id {} not found", bookId);
            throw new NoSuchElementException();
        }
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
    @Override
    public AuthorDto getAuthorById(Long id) {
        log.info("Try to find author by id {}", id);
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent())
        {
            log.info("Author: {}", author.toString());
            return convertToDto(author.get());
        }
        else
        {
            log.error("Author by id {} not found", id);
            throw new NoSuchElementException();
        }
    }

    public List <AuthorDto> getAllAuthors(){
        log.info("Try find all authors");
        List <Author> authors = authorRepository.findAll();
        if (!authors.isEmpty()) {
            log.info("Found {} author(s)", authors.size());
            return authors.stream().map(this::convertToDto).collect(Collectors.toList());
        }
        else {
            log.info("No author found");
            return new ArrayList<>();
        }

    }

    //create
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto){
        log.info("Try to create author with data {}", authorCreateDto.toString());
        Author author = authorRepository.save(convertDtoToEntity(authorCreateDto));
        if (author.getId() != null) {
            log.info("Author created with id {}", author.getId());
            return convertToDto(author);
        } else {
            log.error("Failed to create author");
            throw new RuntimeException("Failed to create author");
        }
    }

    //update
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto){
        log.info("Try to update author by id {} with data {}", authorUpdateDto.getId(), authorUpdateDto.toString());
        Optional<Author> optionalAuthor = authorRepository.findById(authorUpdateDto.getId());
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            author.setName(authorUpdateDto.getName());
            author.setSurname(authorUpdateDto.getSurname());
            Author updatedAuthor = authorRepository.save(author);
            log.info("Author updated: {}", updatedAuthor.toString());
            return convertToDto(updatedAuthor);
        } else {
            log.error("Author by id {} not found", authorUpdateDto.getId());
            throw new NoSuchElementException();
        }
    }

    //delete
    public void deleteAuthorById(Long id) {
        log.info("Try to delete author by id {}", id);
        authorRepository.deleteById(id);
        Optional<Author> deletedAuthor = authorRepository.findById(id);
        if (deletedAuthor.isPresent()) {
            log.error("Failed to delete author by id {}", id);
            throw new RuntimeException("Failed to delete author by id " + id);
        } else {
            log.info("Author by id {} successfully deleted", id);
        }
    }

    private Author convertDtoToEntity(AuthorCreateDto authorCreateDto) {
        return Author.builder()
                .name(authorCreateDto.getName())
                .surname(authorCreateDto.getSurname())
                .build();
    }

}
