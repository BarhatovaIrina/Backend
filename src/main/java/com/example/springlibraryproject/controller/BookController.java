package com.example.springlibraryproject.controller;

import com.example.springlibraryproject.dto.BookCreateDto;
import com.example.springlibraryproject.dto.BookDto;
import com.example.springlibraryproject.dto.BookUpdateDto;
import com.example.springlibraryproject.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class BookController {

    private  final BookService bookService;

    @GetMapping("/book/{id}")
    BookDto getBookById(@PathVariable ("id") Long id) {
            return bookService.getBookById(id);
    }
//    @GetMapping("/book")
//    List<BookDto> getAllBooks() {
//        return bookService.getAllBooks();
//    }

    @GetMapping("/book")
    String getAllBooksView(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "book";
    }

    @PostMapping ("/book/create")
    BookDto createBook(@RequestBody BookCreateDto bookCreateDto) {
        return bookService.createBook(bookCreateDto);
    }

    @PutMapping ("/book/update")
    BookDto updateBook(@RequestBody BookUpdateDto bookUpdateDtoDto) {
        return bookService.updateBook(bookUpdateDtoDto);
    }

    @DeleteMapping("book/delete/{id}")
    public void deleteBookById(@PathVariable ("id") Long id){
        bookService.deleteBookById(id);
    }

}
