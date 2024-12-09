package com.example.book.repositories;

import com.example.book.models.Author;
import com.example.book.models.Genre;
import com.example.book.models.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StoryRepository extends JpaRepository<Story, UUID> {
    Optional<Story> findByName(String name);
    boolean existsByGenre(Genre genre);
    boolean existsByAuthorsContaining(Author author);

}
