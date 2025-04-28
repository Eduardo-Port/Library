package io.github.Eduardo_Port.library.repository;

import io.github.Eduardo_Port.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
