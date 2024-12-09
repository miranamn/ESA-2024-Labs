package com.example.book.controllers;

import com.example.book.dto.GenreDto;
import com.example.book.services.GenreService;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(path = "api/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;
    @GetMapping
    public List<GenreDto> getGenres(){
        return genreService.getAllGenres();
    }
    @PostMapping
    public GenreDto addGenre(@RequestBody GenreDto genre){
        return genreService.addGenre(genre);
    }
    @PutMapping("/{id}")
    public GenreDto updateGenre(@PathVariable("id") UUID id, @RequestBody GenreDto genre){
        return genreService.updateGenre(id, genre);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity DeleteGenre(@PathVariable("id") UUID id){
        return genreService.deleteGenre(id);
    }
}
