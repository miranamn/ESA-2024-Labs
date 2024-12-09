package com.example.book.services;

import com.example.book.dto.AuthorDto;
import com.example.book.dto.GenreDto;
import com.example.book.dto.StoryDto;
import com.example.book.models.Author;
import com.example.book.models.Genre;
import com.example.book.models.Story;
import com.example.book.repositories.AuthorRepository;
import com.example.book.repositories.GenreRepository;
import com.example.book.repositories.StoryRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private GenreRepository genreRepository;
    private AuthorRepository authorRepository;
    @Autowired
    public StoryService(StoryRepository storyRepository, GenreRepository genreRepository, AuthorRepository authorRepository) {
        this.storyRepository = storyRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    public List<StoryDto> getAllStories(){
        return storyRepository.findAll().stream()
                .map(story -> new StoryDto(
                        story.getId(),
                        story.getName(),
                        new GenreDto(story.getGenre().getId(), story.getGenre().getName()),
                        story.getAuthors().stream()
                                .map(author -> new AuthorDto(author.getId(), author.getName(), author.getNickname()))
                                .collect(Collectors.toCollection(LinkedList::new))
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public StoryDto addStory(StoryDto storyDto) {
        if (storyRepository.findByName(storyDto.getName()).isPresent()) {
            throw new EntityExistsException("Story with name '" + storyDto.getName() + "' already exists.");
        }

        Genre savedGenre = genreRepository.findByName(storyDto.getGenre().getName())
                .orElseGet(() -> {
                    Genre genre = new Genre();
                    genre.setName(storyDto.getGenre().getName());
                    return genreRepository.save(genre);
                });

        Story story = new Story();
        story.setName(storyDto.getName());
        story.setGenre(savedGenre);

        LinkedList<Author> authors = storyDto.getAuthors().stream()
                .map(authorDto -> authorRepository.findByNameAndNickname(authorDto.getName(), authorDto.getNickname())
                        .orElseGet(() -> {
                            Author author = new Author();
                            author.setName(authorDto.getName());
                            author.setNickname(authorDto.getNickname());
                            return authorRepository.save(author);
                        }))
                .collect(Collectors.toCollection(LinkedList::new));
        story.setAuthors(authors);
        Story savedStory = storyRepository.save(story);

        return new StoryDto(
                savedStory.getId(),
                savedStory.getName(),
                new GenreDto(savedGenre.getId(), savedGenre.getName()),
                authors.stream()
                        .map(author -> new AuthorDto(author.getId(), author.getName(), author.getNickname()))
                        .collect(Collectors.toCollection(LinkedList::new))
        );
    }

    @Transactional
    public StoryDto updateStory(UUID storyId, String newName) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new EntityNotFoundException("Story with ID '" + storyId + "' not found."));

        if (storyRepository.findByName(newName).isPresent()) {
            throw new EntityExistsException("Story with name '" + newName + "' already exists.");
        }
        story.setName(newName);
        Story updatedStory = storyRepository.save(story);
        return new StoryDto(
                updatedStory.getId(),
                updatedStory.getName(),
                new GenreDto(updatedStory.getGenre().getId(), updatedStory.getGenre().getName()),
                updatedStory.getAuthors().stream()
                        .map(author -> new AuthorDto(author.getId(), author.getName(), author.getNickname()))
                        .collect(Collectors.toCollection(LinkedList::new))
        );
    }

    @Transactional
    public ResponseEntity deleteStory(UUID storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new EntityNotFoundException("Story with ID '" + storyId + "' not found."));
        Genre genre = story.getGenre();
        List<Author> authors = new ArrayList<>(story.getAuthors());

        storyRepository.delete(story);
        if (!storyRepository.existsByGenre(genre)) {
            genreRepository.delete(genre);
        }
        authors.stream()
                .filter(author -> !storyRepository.existsByAuthorsContaining(author))
                .forEach(authorRepository::delete);
        return ResponseEntity.ok().build();
    }
}
