package com.example.book.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "change_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeLog {
    @Id
    @GeneratedValue
    @Column(name = "change_id", unique = true)
    private UUID id;

    private String entity;

    private String changeType; // update, delete, create

    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "Changing{" + "entity ='" + entity + '\n' + ", changeType ='" + changeType + '\n'
                + '\n' + ", timestamp ='" + timestamp + '}';
    }
}
