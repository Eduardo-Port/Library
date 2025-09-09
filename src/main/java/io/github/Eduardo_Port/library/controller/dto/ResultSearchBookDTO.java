package io.github.Eduardo_Port.library.controller.dto;

import io.github.Eduardo_Port.library.model.GenreBook;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResultSearchBookDTO(
        String isbn,
        String title,
        LocalDate publicationDate,
        GenreBook genre,
        BigDecimal price,
        AuthorDTO author
) {
}
