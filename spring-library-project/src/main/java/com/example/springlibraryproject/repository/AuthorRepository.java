package com.example.springlibraryproject.repository;

import com.example.springlibraryproject.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List <Author> findAuthorsByBooksId(Long bookId);

}
