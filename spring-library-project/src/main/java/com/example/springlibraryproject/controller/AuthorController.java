package com.example.springlibraryproject.controller;

import com.example.springlibraryproject.dto.AuthorDto;
import com.example.springlibraryproject.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author/{id}")
    AuthorDto getAuthorById(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/author/book/{id}")
    List<AuthorDto> getAuthorsByBooksId (@PathVariable("id") Long id){
        return authorService.getAuthorByBookId(id);
    }
}