package io.github.Eduardo_Port.library.validator;

import io.github.Eduardo_Port.library.exceptions.DuplicatedRegisterException;
import io.github.Eduardo_Port.library.exceptions.InvalidFieldException;
import io.github.Eduardo_Port.library.model.Book;
import io.github.Eduardo_Port.library.repository.BookRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {
    private final BookRepository repository;
    private static final int YEAR_MANDATORY_PRICE = 2000;
    public void validate(Book book) {
        if(existsByIsbn(book)) {
            throw new DuplicatedRegisterException("ISBN já cadastrado");
        }

        if(isPriceMandatoryNull(book)) {
            throw new InvalidFieldException("price", "The price is mandatory for books published after 2000.");
        }
    }

    private boolean isPriceMandatoryNull(Book book) {
        return book.getPrice() == null &&
                book.getPublicationDate().getYear() >= YEAR_MANDATORY_PRICE;
    }

    private boolean existsByIsbn(Book book) {
        Optional<Book> bookFound = repository.findByIsbn(book.getIsbn());
        if(book.getId() == null) {
            return bookFound.isPresent();
        } else {
            return bookFound
                    .map(Book::getId)
                    .stream()
                    .anyMatch(id -> !id.equals(book.getId()));
        }
    }
}
