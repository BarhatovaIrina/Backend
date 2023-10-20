package com.example.springJDBCproject.repository;

import com.example.springJDBCproject.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {
//    @Autowired
    private DataSource dataSource;

    public BookRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Book> findAllBooks() {
        List <Book> result = new ArrayList<>();
        String query = "select * from books";
        try {
            Connection conn = dataSource.getConnection();
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery(query);
            while(resultSet.next()){
                Book book = convertRowToBook(resultSet);
                result.add(book);
            }
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    @Override
    public Book findBookById(Long index) {
        Book book=new Book();
        String query = "select * from books where id="+String.valueOf(index);
        System.out.println(query);
        try {
            Connection conn = dataSource.getConnection();
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery(query);
            if (resultSet.next()) {
                book = convertRowToBook(resultSet);
                System.out.println(book.getName());
            }
        }
        catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return book;
    }
    private Book convertRowToBook(ResultSet resultSet) throws SQLException{
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Book(id, name);
      //  return  new Book(1L,"125553");
    }
}
