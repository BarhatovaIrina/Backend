package com.example.springlibraryproject.repository;

import com.example.springlibraryproject.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor <Author> {
    List <Author> findAuthorsByBooksId(Long bookId);

    // ***** способы выполнить запросы ************************//
    Optional<Author> findAuthorBySurname(String surname);

    @Query(nativeQuery = true, value="SELECT * FROM AUTHOR WHERE surname=?")
    Optional<Author> findAuthorBySurnameBySql(String surname);

}
