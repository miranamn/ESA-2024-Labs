package com.example.book.services;

import com.example.book.config.ChangeLogging;
import com.example.book.dto.GenreDto;
import com.example.book.models.Genre;
import com.example.book.repositories.GenreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {
    GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreDto> getAllGenres(){
        return genreRepository.findAll().stream()
                .map(genre -> new GenreDto(genre.getId(), genre.getName()))
                .collect(Collectors.toList());
    }
    @Transactional
    @ChangeLogging
    public GenreDto addGenre(GenreDto genreDto) {
        Genre genre = new Genre();
        genre.setName(genreDto.getName());
        Genre savedGenre = genreRepository.save(genre);
        return new GenreDto(savedGenre.getId(), savedGenre.getName());
    }
    @Transactional
    @ChangeLogging
    public GenreDto updateGenre(UUID id, GenreDto genreDto) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found with id: " + id));

        genre.setName(genreDto.getName());
        Genre updatedGenre = genreRepository.save(genre);
        return new GenreDto(updatedGenre.getId(), updatedGenre.getName());
    }
    @Transactional
    @ChangeLogging
    public ResponseEntity deleteGenre(UUID id) {
        genreRepository.delete(genreRepository.findById(id).orElseThrow(()-> new IllegalStateException(
                "genre with id " + id + " does not exists")));
        return ResponseEntity.ok().build();
    }
}
