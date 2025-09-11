package io.github.Eduardo_Port.library.repository.specs;

import io.github.Eduardo_Port.library.model.Author;
import io.github.Eduardo_Port.library.model.Book;
import io.github.Eduardo_Port.library.model.GenreBook;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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
    public static Specification<Book> publicationDateEqual(Integer publicationDate) {
        return (root, query, cb) ->
                cb.equal(
                        cb.function("to_char", String.class, root.get("publicationDate"), cb.literal("YYYY")),
                        publicationDate.toString());
    }
    public static Specification<Book> authorNameLike(String name) {
        return (root, query, cb) -> {
            Join<Book, Author> joinAuthor = root.join("author", JoinType.LEFT);
            return cb.like( cb.upper(joinAuthor.get("name")), "%" + name.toUpperCase() + "%");


//            return cb.like(cb.upper(root.get("author").get("name")), "%" + name.toUpperCase() + "%");
        };
    }
}
