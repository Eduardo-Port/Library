package io.github.Eduardo_Port.library.repository.specs;

import io.github.Eduardo_Port.library.model.Book;
import io.github.Eduardo_Port.library.model.GenreBook;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {
    public static Specification<Book> isbnEqual(String isbn) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn);
    }
    public static Specification<Book> titleLike(String title) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("title")), "%" + title.toUpperCase() + "%"));
    }
    public static Specification<Book> genreEqual(GenreBook genre) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("genre"), genre));
    }

}
