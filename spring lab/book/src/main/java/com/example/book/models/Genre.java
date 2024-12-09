package com.example.book.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "genres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    @GeneratedValue
    @Column(name = "genre_id", unique = true)
    private UUID id;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "genre")
    private List<Story> stories;
}

