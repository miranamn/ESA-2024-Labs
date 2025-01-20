package com.example.book.repositories;

import com.example.book.models.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChangeLogRepository extends JpaRepository<ChangeLog, UUID> {
}
