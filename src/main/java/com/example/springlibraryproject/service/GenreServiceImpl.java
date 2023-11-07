package com.example.springlibraryproject.service;

import com.example.springlibraryproject.dto.*;
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

    @Override
    public GenreDto createGenre(GenreCreateDto genreCreateDto) {
        Genre genre = genreRepository.save(convertDtoToEntity(genreCreateDto));
        return convertToDto(genre);
    }

    @Override
    public GenreDto updateGenre(GenreUpdateDto genreUpdateDto) {
        Genre genre = genreRepository.findById(genreUpdateDto.getId()).orElseThrow();
        genre.setName(genreUpdateDto.getName());
        Genre updateGenre = genreRepository.save(genre);
        return  convertToDto(updateGenre);
    }

//    @Override
//    public void deleteGenreById(Long id) {
//        genreRepository.deleteById(id);
//    }

    @Override
    public List<GenreDto> getAllGenres() {
        List <Genre> genres = genreRepository.findAll();
        return convertToDto(genres);
    }

    private Genre convertDtoToEntity(GenreCreateDto genreCreateDto) {
        return Genre.builder()
                .name(genreCreateDto.getName())
                .build();
    }

    private List <GenreDto> convertToDto(List <Genre> genres){
        List <GenreDto> genreDtoList =  genres.stream().
                map(genre -> GenreDto.builder()
                            .name(genre.getName())
                            .id(genre.getId())
                            .build()
        ).toList();
        return genreDtoList;
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
