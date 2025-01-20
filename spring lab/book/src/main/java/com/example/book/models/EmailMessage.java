package com.example.book.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Entity
@Table(name = "change_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessage {
    @Id
    @GeneratedValue
    @Column(name = "email_id", unique = true)
    private UUID id;
    private String address;
    private String content;

    @Override
    public String toString() {
        return String.format("Email{address=%s, content=%s}", address, content);
    }
}
