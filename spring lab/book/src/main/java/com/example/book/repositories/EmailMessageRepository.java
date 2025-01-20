package com.example.book.repositories;

import com.example.book.models.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailMessageRepository extends JpaRepository<EmailMessage, UUID> {
}
