package com.example.book.services;

import com.example.book.dto.AuthorDto;
import com.example.book.models.Author;
import com.example.book.repositories.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public List<AuthorDto> getAllAuthors(){
        return authorRepository.findAll().stream()
                .map(author -> new AuthorDto(author.getId(), author.getName(), author.getNickname()))
                .collect(Collectors.toList());
    }
    @Transactional
    public AuthorDto addAuthor(AuthorDto authorDto) {
        Author author = new Author();
        author.setName(authorDto.getName());
        author.setNickname(authorDto.getNickname());
        Author savedAuthor = authorRepository.save(author);
        return new AuthorDto(savedAuthor.getId(), savedAuthor.getName(), author.getNickname());
    }
    @Transactional
    public AuthorDto updateAuthor(UUID id, AuthorDto authorDto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found with id: " + id));

        author.setName(authorDto.getName());
        author.setNickname(authorDto.getNickname());
        Author updatedAuthor = authorRepository.save(author);
        return new AuthorDto(updatedAuthor.getId(), updatedAuthor.getName(), updatedAuthor.getNickname());
    }
    @Transactional
    public ResponseEntity deleteAuthor(UUID id) {
        authorRepository.delete(authorRepository.findById(id).orElseThrow(()-> new IllegalStateException(
                "Author with id " + id + " does not exists")));
        return ResponseEntity.ok().build();
    }
}
