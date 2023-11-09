package com.example.springlibraryproject.service;

import com.example.springlibraryproject.dto.AuthorCreateDto;
import com.example.springlibraryproject.dto.AuthorDto;
import com.example.springlibraryproject.dto.AuthorUpdateDto;
import com.example.springlibraryproject.model.Author;
import com.example.springlibraryproject.model.Book;
import com.example.springlibraryproject.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    public void testGetAuthorById(){
        //Алгоритм теста testGetAuthorByUd() заключается в проверке, что метод getAuthorById() из сервиса authorService
        // корректно возвращает объект AuthorDto для заданного id автора.
        Long id =1L;
        String name = "Petr";
        String surname = "Petrov";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);
        // устанавливаем поведение мок объекта
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        //вызываем соответствущий метод
        AuthorDto authorDto = authorService.getAuthorById(id);
        // проверяем что метод findById был вызван у мок объекта
        verify(authorRepository).findById(id);
        //проверка за соответствие результата
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }
    @Test
    public void testGetAuthorByIdNotFound() {
        Long id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getAuthorById(id));
        verify(authorRepository).findById(id);
    }
    @Test
    public void testGetAllAuthors() {
        Author author = new Author(1L, "Petr", "Petrov", new HashSet<>());
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        when(authorRepository.findAll()).thenReturn(authors);
        List <AuthorDto> authorDtoList = authorService.getAllAuthors();
        verify(authorRepository).findAll();
        Assertions.assertEquals(authorDtoList.size(), authors.size());
    }
    @Test
    public void testGetAllAuthorsNotFound() {
        List<Author> authors = new ArrayList<>();
        when(authorRepository.findAll()).thenReturn(authors);
        List <AuthorDto> authorDtoList = authorService.getAllAuthors();
        verify(authorRepository).findAll();
        Assertions.assertTrue(authorDtoList.isEmpty());
    }

    @Test
    public void testCreateAuthor(){
        Author author = new Author(1L, "Pert", "Petrov", new HashSet<>());
        AuthorCreateDto authorCreateDto = new AuthorCreateDto(1L, "Pert", "Petrov");
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        AuthorDto createdAuthorDto = authorService.createAuthor(authorCreateDto);
        verify(authorRepository).save(any(Author.class));
        Assertions.assertEquals(authorCreateDto.getName(), createdAuthorDto.getName());
        Assertions.assertEquals(authorCreateDto.getSurname(), createdAuthorDto.getSurname());
    }
    @Test
    public void testCreateAuthorFailed() {
        when(authorRepository.save(any(Author.class))).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class, () -> authorService.createAuthor(new AuthorCreateDto()));
        verify(authorRepository).save(any(Author.class));
    }

    @Test
    public void testUpdateAuthor() {
        Author author = new Author(1L, "Pert", "Petrov", new HashSet<>());
        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto(1L, "Pert", "Petrov", null);
        when(authorRepository.findById(authorUpdateDto.getId())).thenReturn(Optional.of(author));

        AuthorDto updatedAuthorDto = authorService.updateAuthor(authorUpdateDto);
        verify(authorRepository).findById(authorUpdateDto.getId());
        verify(authorRepository).save(any(Author.class));
        Assertions.assertEquals(authorUpdateDto.getName(), updatedAuthorDto.getName());
        Assertions.assertEquals(authorUpdateDto.getSurname(), updatedAuthorDto.getSurname());
    }

    @Test
    public void testUpdateAuthorFailed () {
        Long id = 1L;
        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto(1L, "Pert", "Petrov", null);
        when(authorRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            authorService.updateAuthor(authorUpdateDto);
        });
        verify(authorRepository).findById(id);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    public void testDeleteAuthorById() {
        Long id=1L;
        authorService.deleteAuthorById(id);
        verify(authorRepository).deleteById(id);
    }
    @Test
    public void testDeleteAuthorByIdFailed(){
        Long id=1L;
        Author author = new Author(1L, "Pert", "Petrov", new HashSet<>());
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        Assertions.assertThrows(RuntimeException.class, () -> {
            authorService.deleteAuthorById(id);
        });
        verify(authorRepository).findById(id);
        verify(authorRepository).deleteById(id);
    }
}
