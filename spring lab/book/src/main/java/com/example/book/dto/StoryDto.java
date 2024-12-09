package com.example.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.UUID;

@Data
@AllArgsConstructor
public class StoryDto {
    private UUID id;
    private String name;
    private GenreDto genre;
    private LinkedList<AuthorDto> authors;
}
