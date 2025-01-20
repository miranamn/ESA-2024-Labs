package com.example.book.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "stories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Story {

    @Id
    @GeneratedValue
    @Column(name = "story_id", unique = true)
    private UUID id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "authors_stories",
            joinColumns = { @JoinColumn(name = "story_id") },
            inverseJoinColumns = { @JoinColumn(name = "author_id") })
    private List<Author> authors = new LinkedList<>();
}
