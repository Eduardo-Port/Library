package io.github.Eduardo_Port.library.repository;

import io.github.Eduardo_Port.library.model.Author;
import io.github.Eduardo_Port.library.model.Book;
import io.github.Eduardo_Port.library.model.GenreBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@SpringBootTest
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    @Transactional
    @Commit
    void saveTest() {
        Book book = new Book();
        book.setTitle("Piraporã");
        book.setGenre(GenreBook.MYSTERY);
        book.setPublicationDate(LocalDate.of(1930, 1, 20));
        book.setIsbn("1253");
        book.setPrice(BigDecimal.valueOf(1922.99));
        Author author = authorRepository.findById(UUID.fromString("7b069bc8-cf41-47f5-9cc2-99eeb074470c"))
                .orElse(null);
        book.setAuthor(author);
        var bookSaved = bookRepository.save(book);
        System.out.println("Book saved: " + bookSaved);
    }

    @Test
    @Transactional
    @Commit
    void saveCascadeTest() {
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
        var bookSaved = bookRepository.save(book);
        System.out.println("Book saved: " + bookSaved);
    }

    @Test
    @Transactional
    @Commit
    void changeAuthorBook() {
        var bookToUpdate = bookRepository.findById(UUID.fromString("aecbc6bc-66be-4194-a3bd-1bca7a5dcfff"))
                .orElse(null);
        Author authorUpdated = authorRepository.findById(UUID.fromString("7b069bc8-cf41-47f5-9cc2-99eeb074470c"))
                .orElse(null);
        bookToUpdate.setAuthor(authorUpdated);

        bookRepository.save(bookToUpdate);
    }

    @Test
    void delete() {
        var bookToDelete = bookRepository.findById(UUID.fromString("aecbc6bc-66be-4194-a3bd-1bca7a5dcfff"))
                .orElse(null);
        if (bookToDelete != null) {
            bookRepository.delete(bookToDelete);
        } else {
            System.out.println("Book not found");
        }
    }

    @Test
    @Transactional
    void findBookTest() {
        UUID id = UUID.fromString("d3fae6b8-b019-4067-97fc-5c330f48f73f");
        Book book = bookRepository.findById(id)
                .orElse(null);
        System.out.println("Book found: " + book.getTitle());

        System.out.println("Author: " + book.getAuthor().getName());
    }

    @Test
    @Transactional
    void listBooksByAuthor() {
        var id = UUID.fromString("9223fac5-16d3-497c-a574-11c5874a134e");
        var author = authorRepository.findById(id).get();
        List<Book> books = bookRepository.findByAuthor(author);
        author.setBooksPublished(books);


        author.getBooksPublished().forEach(System.out::println);
    }

    @Test
    void searchByTitle() {
        String title = "Stone";
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        books.forEach(System.out::println);
    }

    @Test
    void listByGenreQueryParamTest() {
        var result = bookRepository.findByGenre(GenreBook.FANTASY, "publicationDate");
        result.forEach(System.out::println);
    }

    @Test
    void listByGenrePositionalParameters() {
        var result = bookRepository.findByGenrePositionalParameters(GenreBook.FANTASY, "publicationDate");
        result.forEach(System.out::println);
    }

    @Test
    void deleteByGenreTest() {
        bookRepository.deleteByGenre(GenreBook.FANTASY);
    }
}