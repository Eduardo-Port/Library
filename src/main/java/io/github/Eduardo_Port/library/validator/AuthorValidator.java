package io.github.Eduardo_Port.library.validator;

import io.github.Eduardo_Port.library.exceptions.DuplicatedRegisterException;
import io.github.Eduardo_Port.library.model.Author;
import io.github.Eduardo_Port.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorValidator {
    private final AuthorRepository repository;

    public void validate(Author author) {
        if(existsAuthorRegistered(author)) {
            throw new DuplicatedRegisterException("Author already registered");
        }
    }

    private boolean existsAuthorRegistered(Author author) {
        Optional<Author> authorFounded = repository.findByNameAndDateBirthAndNationality(
                author.getName(), author.getDateBirth(), author.getNationality()
        );
        if(author.getId() == null) {
            return authorFounded.isPresent();
        }

        return !author.getId().equals(authorFounded.get().getId()) && authorFounded.isPresent();
    }
}
