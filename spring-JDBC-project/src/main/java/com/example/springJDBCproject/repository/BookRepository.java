package com.example.springJDBCproject.repository;
import com.example.springJDBCproject.model.Book;
import java.util.List;

public interface BookRepository {
    List<Book> findAllBooks();

    Book findBookById(Long index);
}
