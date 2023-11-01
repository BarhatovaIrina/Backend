package com.example.springlibraryproject.repository;

import com.example.springlibraryproject.dto.BookDto;
import com.example.springlibraryproject.model.Author;
import com.example.springlibraryproject.model.Book;
import com.example.springlibraryproject.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository  extends JpaRepository<Book, Long> {
    List <Book> findByGenre(Genre genre);

}
