package io.github.Eduardo_Port.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
public class Author {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(name = "date_birth", nullable = false)
    private LocalDate dateBirth;
    @Column(length = 50, nullable = false)
    private String nationality;
    @OneToMany(mappedBy = "author")
    private List<Book> booksPublished;
}
