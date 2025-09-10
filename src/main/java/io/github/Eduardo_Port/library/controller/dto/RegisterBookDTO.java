package io.github.Eduardo_Port.library.controller.dto;

import io.github.Eduardo_Port.library.model.GenreBook;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RegisterBookDTO (
        @NotBlank(message = "obrigatory field")
        String isbn,
        @NotBlank(message = "obrigatory field")
        String title,
        @NotNull(message = "obrigatory field")
                @Past(message = "date must be in the past")
        LocalDate publicationDate,
        GenreBook genre,
        BigDecimal price,
        @NotNull(message = "obrigatory field")
        UUID authorId
) {

}
