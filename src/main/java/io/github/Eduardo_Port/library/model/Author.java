package io.github.Eduardo_Port.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@ToString(exclude = "booksPublished")
@EntityListeners(AuditingEntityListener.class)
public class Author {
    private final static LocalDate MAX_DATEBIRTH = LocalDate.of(2022, 10, 1);
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

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.PERSIST
    )
    private List<Book> booksPublished;

    @CreatedDate
    @Column(name = "date_register")
    private LocalDateTime registerDate;
    @LastModifiedDate
    @Column(name = "date_update")
    private LocalDateTime updateDate;
    @Column(name = "id_user")
    private UUID idUser;

    public void update(String name, LocalDate dateBirth, String nationality) {
        this.name = name;

        this.dateBirth = dateBirth;


        this.nationality = nationality;

    }
}
