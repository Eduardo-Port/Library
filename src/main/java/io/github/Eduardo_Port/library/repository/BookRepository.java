package io.github.Eduardo_Port.library.repository;

import io.github.Eduardo_Port.library.model.Author;
import io.github.Eduardo_Port.library.model.Book;
import io.github.Eduardo_Port.library.model.GenreBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {
    List<Book> findByAuthor(Author author);

    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query("select b from Book b where b.genre = :genre order by :paramOrdenation ")
    List<Book> findByGenre(
            @Param("genre") GenreBook genre,
            @Param("paramOrdenation") String nameProperties
    );

    @Query("select b from Book b where b.genre = ?1 order by ?2 ")
    List<Book> findByGenrePositionalParameters(GenreBook genreBook, String nameProperties);

    @Modifying
    @Transactional
    @Query("delete from Book where genre = ?1")
    void deleteByGenre(GenreBook genreBook);

    boolean existsByAuthor(Author author);

    Optional<Book> findByIsbn(String isbn);
}
