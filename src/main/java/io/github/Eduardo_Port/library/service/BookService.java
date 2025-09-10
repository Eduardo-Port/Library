package io.github.Eduardo_Port.library.service;

import io.github.Eduardo_Port.library.controller.dto.RegisterBookDTO;
import io.github.Eduardo_Port.library.model.Book;
import io.github.Eduardo_Port.library.model.GenreBook;
import io.github.Eduardo_Port.library.repository.BookRepository;
import io.github.Eduardo_Port.library.repository.specs.BookSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    public void save(Book book) {
        bookRepository.save(book);
    }
    public Optional<Book> getById(UUID id) {
        return bookRepository.findById(id);
    }
    public void delete(UUID id) {
        bookRepository.deleteById(id);
    }
    //isbn, title, authorName, publisherName, yearPublished
    public List<Book> search(String isbn, String authorName, String title, Integer yearPublished, GenreBook genre) {
//        Specification<Book> specs = Specification.where(BookSpecs.isbnEqual(isbn))
//                .and(BookSpecs.titleLike(title)
//                        .and(BookSpecs.genreEqual(genre)));

        Specification<Book> specs = Specification.where((root, query, cb) -> cb.conjunction());
        if(isbn != null) {
            specs = specs.and(BookSpecs.isbnEqual(isbn));
        }
        if(title != null) {
            specs = specs.and(BookSpecs.titleLike(title));
        }
        if(genre!=null) {
            specs = specs.and(BookSpecs.genreEqual(genre));
        }

        return bookRepository.findAll(specs);
    }
}
