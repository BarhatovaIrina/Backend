package com.example.springlibraryproject.service;

import com.example.springlibraryproject.dto.AuthorDto;
import com.example.springlibraryproject.dto.BookDto;
import com.example.springlibraryproject.dto.GenreDto;
import com.example.springlibraryproject.model.Author;
import com.example.springlibraryproject.model.Book;
import com.example.springlibraryproject.model.Genre;
import com.example.springlibraryproject.repository.AuthorRepository;
import com.example.springlibraryproject.repository.BookRepository;
import com.example.springlibraryproject.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Override
    public GenreDto getGenreById(Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        return convertToDto(genre);
    }


    private GenreDto convertToDto(Genre genre) {
        List<Book> books = bookRepository.findByGenre(genre);
        System.out.println(books.size());
        List <BookDto> bookDtoList = new ArrayList<>();
        for (Book book:books) {
            BookDto bookDto = new BookDto();
            bookDto.setId(book.getId());
            bookDto.setName(book.getName());
            bookDto.setGenre(book.getGenre().getName());
            bookDto.setAuthors(authorService.getAuthorByBookId(book.getId()));
            bookDtoList.add(bookDto);
        }

        return GenreDto.builder()
                .books(bookDtoList)
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
