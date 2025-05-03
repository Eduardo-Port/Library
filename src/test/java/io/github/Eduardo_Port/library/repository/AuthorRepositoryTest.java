package io.github.Eduardo_Port.library.repository;

import io.github.Eduardo_Port.library.model.Author;
import io.github.Eduardo_Port.library.model.Book;
import io.github.Eduardo_Port.library.model.GenreBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authorRepository;

    @Test
    public void saveTest() {
        Author author = new Author();
        author.setName("J.K. Rowlingtest");
        author.setNationality("British");
        author.setDateBirth(LocalDate.of(1965, 7, 31));

        var authorSaved = authorRepository.save(author);
        System.out.println("Author saved: " + authorSaved);
    }

    @Test
    public void updateTest() {
        var id = UUID.fromString("e4d41bdd-6799-4ffd-bb8a-00d515cd3881");

        Optional<Author> author = authorRepository.findById(id);

        if(author.isPresent()) {
            Author authorToUpdate = author.get();
            System.out.println("Author found: " + author.get());

            authorToUpdate.setDateBirth(LocalDate.of(1960, 1, 30));
            authorRepository.save(authorToUpdate);
        } else {
            System.out.println("Author not found");
        }
    }

    @Test
    public void listTest() {
        List<Author> list = authorRepository.findAll();
        list.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Contagem de autores: " + authorRepository.count());
    }

    @Test
    public void deleteByIdTest() {
        var id = UUID.fromString("e4d41bdd-6799-4ffd-bb8a-00d515cd3881");
        authorRepository.deleteById(id);
    }

    @Test
    public void deleteTest() {
        var id = UUID.fromString("e4d41bdd-6799-4ffd-bb8a-00d515cd3881");
        var author = authorRepository.findById(id).get();
        authorRepository.delete(author);
    }

    @Test
    @Transactional
    void saveAuthorWithBooksTest() {
        Author author = new Author();
        author.setName("Isabella Lima");
        author.setNationality("Brasilera");
        author.setDateBirth(LocalDate.of(1965, 7, 27));

        Book book = new Book();
        book.setIsbn("2312-4321");
        book.setTitle("askdlsajkldjklsadjklsa");
        book.setPrice(BigDecimal.valueOf(1900.99));
        book.setAuthor(author);
        book.setGenre(GenreBook.ROMANCE);
        book.setPublicationDate(LocalDate.of(2023, 10, 1));

        Book book2 = new Book();
        book2.setIsbn("2354-32345");
        book2.setTitle("Feito com amor");
        book2.setAuthor(author);
        book2.setPrice(BigDecimal.valueOf(16.99));
        book2.setGenre(GenreBook.MYSTERY);
        book2.setPublicationDate(LocalDate.of(2023, 12, 23));

        author.setBooksPublished(new ArrayList<>());
        author.getBooksPublished().add(book);
        author.getBooksPublished().add(book2);

        var authorSaved = authorRepository.save(author);
        System.out.println("Author with books saved: " + authorSaved + "\nBooks saved: " + authorSaved.getBooksPublished());
    }

}
