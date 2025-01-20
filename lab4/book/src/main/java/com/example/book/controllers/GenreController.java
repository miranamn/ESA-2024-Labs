package com.example.book.controllers;

import com.example.book.dto.GenreDto;
import com.example.book.services.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(
        value = "api",
        produces = {"application/xml", "application/json"}
)
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genre")
    public @ResponseBody List<GenreDto> getGenres(){
        return genreService.getAllGenres();
    }

    @PostMapping("/genre")
    public @ResponseBody GenreDto addGenre(@RequestBody GenreDto genre){
        return genreService.addGenre(genre);
    }

    @PutMapping("/genre/{id}")
    public @ResponseBody GenreDto updateGenre(@PathVariable("id") UUID id, @RequestBody GenreDto genre){
        return genreService.updateGenre(id, genre);
    }

    @DeleteMapping("/genre/{id}")
    public @ResponseBody ResponseEntity DeleteGenre(@PathVariable("id") UUID id){
        return genreService.deleteGenre(id);
    }
}
