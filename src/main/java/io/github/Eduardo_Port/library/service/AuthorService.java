package io.github.Eduardo_Port.library.service;

import io.github.Eduardo_Port.library.model.Author;
import io.github.Eduardo_Port.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository repository;
    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public Author save(Author author) {
        return repository.save(author);
    }

    public Optional<Author> findById(UUID idAuthor) {
        return repository.findById(idAuthor);
    }

    public void delete(Author author) {
        repository.delete(author);
    }

    public List<Author> search(String name, String nationality) {
        if(name != null && nationality != null) {
            return repository.findByNameAndNationality(name, nationality);
        }
        if(name != null) {
            return repository.findByName(name);
        }
        if(nationality != null) {
            return repository.findByNationality(nationality);
        }

        return repository.findAll();
    }

    public void update(Author author) {
        if(author.getId() == null) {
            throw new IllegalArgumentException("Author ID cannot be null");
        }
        repository.save(author);
    }
}
