package io.github.Eduardo_Port.library.service;

import io.github.Eduardo_Port.library.exceptions.OperationNotAllowed;
import io.github.Eduardo_Port.library.model.Author;
import io.github.Eduardo_Port.library.repository.AuthorRepository;
import io.github.Eduardo_Port.library.repository.BookRepository;
import io.github.Eduardo_Port.library.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorValidator validator;
    private final BookRepository bookRepository;

    public Author save(Author author) {
        validator.validate(author);
        return authorRepository.save(author);
    }

    public Optional<Author> findById(UUID idAuthor) {
        return authorRepository.findById(idAuthor);
    }

    public void delete(Author author) {
        if(haveBooksPublished(author)) {
            throw new OperationNotAllowed("Cannot delete author with published books.");
        }
        authorRepository.delete(author);
    }

    public void update(Author author) {
        if(author.getId() == null) {
            throw new IllegalArgumentException("Author ID cannot be null.");
        }
        validator.validate(author);
        authorRepository.save(author);
    }

    public List<Author> search(String name, String nationality) {
        if(name != null && nationality != null) {
            return authorRepository.findByNameAndNationality(name, nationality);
        }
        if(name != null) {
            return authorRepository.findByName(name);
        }
        if(nationality != null) {
            return authorRepository.findByNationality(nationality);
        }

        return authorRepository.findAll();
    }

    public List<Author> searchByExample(String name, String nationality) {
        var author = new Author();
        author.setName(name);
        author.setNationality(nationality);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();
        Example<Author> authorExample = Example.of(author, matcher);
        return authorRepository.findAll(authorExample);
    }

    public boolean haveBooksPublished(Author author) {
        return bookRepository.existsByAuthor(author);
    }
}
