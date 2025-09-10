package io.github.Eduardo_Port.library.controller.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(UUID id,
                        @NotBlank(message = "Name is a mandatory field.")
                        @Size(max = 100, min = 2, message = "Name must be between 2 and 100 characters.")
                        String name,
                        @NotNull(message = "Birth date is a mandatory field.")
                        @Past(message = "Date of birth must be in the past.")
                        LocalDate dateBirth,
                        @NotBlank(message = "Nationality is a mandatory field.")
                        @Size(max = 50, min = 2, message = "Nationality must be between 2 and 50 characters.")
                        String nationality) {

}
