package com.example.book.services;

import com.example.book.config.ChangeLogging;
import com.example.book.dto.AuthorDto;
import com.example.book.models.Author;
import com.example.book.repositories.AuthorRepository;
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
public class AuthorService {
    AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDto> getAllAuthors(){
        return authorRepository.findAll().stream()
                .map(author -> new AuthorDto(author.getId(), author.getName(), author.getNickname()))
                .collect(Collectors.toList());
    }
    @Transactional
    @ChangeLogging
    public AuthorDto addAuthor(AuthorDto authorDto) {
        Author author = new Author();
        author.setName(authorDto.getName());
        author.setNickname(authorDto.getNickname());
        Author savedAuthor = authorRepository.save(author);
        return new AuthorDto(savedAuthor.getId(), savedAuthor.getName(), author.getNickname());
    }
    @Transactional
    @ChangeLogging
    public AuthorDto updateAuthor(UUID id, AuthorDto authorDto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        author.setName(authorDto.getName());
        author.setNickname(authorDto.getNickname());
        Author updatedAuthor = authorRepository.save(author);
        return new AuthorDto(updatedAuthor.getId(), updatedAuthor.getName(), updatedAuthor.getNickname());
    }
    @Transactional
    @ChangeLogging
    public ResponseEntity deleteAuthor(UUID id) {
        authorRepository.delete(authorRepository.findById(id).orElseThrow(()-> new IllegalStateException(
                "Author with id " + id + " does not exists")));
        return ResponseEntity.ok().build();
    }
}
