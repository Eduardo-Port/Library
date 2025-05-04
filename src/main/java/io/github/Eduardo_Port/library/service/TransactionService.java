package io.github.Eduardo_Port.library.service;

import io.github.Eduardo_Port.library.model.Author;
import io.github.Eduardo_Port.library.model.Book;
import io.github.Eduardo_Port.library.model.GenreBook;
import io.github.Eduardo_Port.library.repository.AuthorRepository;
import io.github.Eduardo_Port.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void updateWithoutUpdate() {
        var book = bookRepository.findById(UUID.fromString("f5f4f226-f889-456d-8420-78179b7022bd")).orElse(null);
        book.setPublicationDate(LocalDate.of(2011, 3, 10));
    }

    @Transactional
    public void execute() {
        Book book = new Book();
        book.setTitle("1984");
        book.setGenre(GenreBook.FANTASY);
        book.setPublicationDate(LocalDate.of(1980, 1, 20));
        book.setIsbn("1234");
        book.setPrice(BigDecimal.valueOf(19.99));

        Author author = new Author();
        author.setName("George Orwell");
        author.setNationality("British");
        author.setDateBirth(LocalDate.of(1965, 7, 31));

        book.setAuthor(author);
        if(author.getName().equals("Eduardo")) {
            throw new RuntimeException("Rollback transaction");
        }
        var bookSaved = bookRepository.save(book);
    }
}
