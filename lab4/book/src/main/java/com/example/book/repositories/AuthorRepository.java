package com.example.book.repositories;


import com.example.book.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Optional<Author> findByNameAndNickname(String name, String nickname);
}
