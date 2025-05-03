package io.github.Eduardo_Port.library.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
@Data
@ToString(exclude = "author")
public class Book {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 20, nullable = false)
    private String isbn;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(name = "date_publication", nullable = false)
    private LocalDate publicationDate;
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private GenreBook genre;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne(
            cascade = CascadeType.PERSIST,
        fetch = FetchType.LAZY, optional = false
    )
    @JoinColumn(name = "id_author", nullable = false)
    private Author author;
}