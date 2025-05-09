package io.github.Eduardo_Port.library.controller.dto;

import io.github.Eduardo_Port.library.model.Author;
import io.github.Eduardo_Port.library.service.AuthorService;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(UUID id,
                        String name,
                        LocalDate dateBirth,
                        String nationality) {

    public Author toAuthor() {
        Author author = new Author();
        author.setName(this.name);
        author.setNationality(this.nationality);
        author.setDateBirth(dateBirth);
        return author;
    }
}
