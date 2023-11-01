package com.example.springlibraryproject.controller;

import com.example.springlibraryproject.dto.AuthorCreateDto;
import com.example.springlibraryproject.dto.AuthorDto;
import com.example.springlibraryproject.dto.AuthorUpdateDto;
import com.example.springlibraryproject.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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

    @GetMapping("author/v1")
    AuthorDto getAuthorByNameV1(@RequestParam("surname") String surname){
        return authorService.getAuthorBySurnameV1(surname);
    }

    @GetMapping("author/v2")
    AuthorDto getAuthorByNameV2(@RequestParam("surname") String surname){
        return authorService.getAuthorBySurnameV2(surname);
    }
    @GetMapping("author/v3")
    AuthorDto getAuthorByNameV3(@RequestParam("surname") String surname){
        return authorService.getAuthorBySurnameV3(surname);
    }

    // *******   CRUD   ********
    @PostMapping("/author/create")
    AuthorDto createAuthor(@RequestBody AuthorCreateDto authorCreateDto) {
        return authorService.createAuthor(authorCreateDto);
    }

    @PutMapping("author/update")
    AuthorDto updateAuthor (@RequestBody AuthorUpdateDto authorUpdateDto) {
        return authorService.updateAuthor(authorUpdateDto);
    }

    @DeleteMapping ("author/delete/{id}")
    public void deleteAuthorById(@PathVariable ("id") Long id){
        authorService.deleteAuthorById(id);
    }

    @GetMapping("/author")
    String getAllAuthorsView (Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "author";
    }
}