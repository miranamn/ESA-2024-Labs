package com.example.book.controllers;

import com.example.book.dto.AuthorDto;
import com.example.book.services.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDto> getAuthors(){
        return authorService.getAllAuthors();
    }
    @PostMapping
    public AuthorDto addAuthor(@RequestBody AuthorDto author){
        return authorService.addAuthor(author);
    }
    @PutMapping("/{id}")
    public AuthorDto updateAuthor(@PathVariable("id") UUID id, @RequestBody AuthorDto author){
        return authorService.updateAuthor(id, author);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity DeleteAuthor(@PathVariable("id") UUID id){
        return authorService.deleteAuthor(id);
    }
}
