package io.github.Eduardo_Port.library.repository;

import io.github.Eduardo_Port.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    List<Author> findByName(String nome);
    List<Author> findByNationality(String nacionalidade);
    List<Author> findByNameAndNationality(String nome, String nacionalidade);
}
