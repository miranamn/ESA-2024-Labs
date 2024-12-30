package com.example.book.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue
    @Column(name = "author_id", unique = true)
    private UUID id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="nickname", nullable = false)
    private String nickname;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "authors")
    private List<Story> stories = new LinkedList<>();
}