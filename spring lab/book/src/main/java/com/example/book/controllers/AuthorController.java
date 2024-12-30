package com.example.book.controllers;

import com.example.book.dto.AuthorDto;
import com.example.book.services.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(
        value = "api",
        produces = {"application/xml", "application/json"}
)
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/author")
    public @ResponseBody List<AuthorDto> getAuthors(){
        return authorService.getAllAuthors();
    }

    @PostMapping("/author")
    public @ResponseBody AuthorDto addAuthor(@RequestBody AuthorDto author){
        return authorService.addAuthor(author);
    }

    @PutMapping("/author/{id}")
    public @ResponseBody AuthorDto updateAuthor(@PathVariable("id") UUID id, @RequestBody AuthorDto author){
        return authorService.updateAuthor(id, author);
    }

    @DeleteMapping("/author/{id}")
    public @ResponseBody ResponseEntity DeleteAuthor(@PathVariable("id") UUID id){
        return authorService.deleteAuthor(id);
    }
}
