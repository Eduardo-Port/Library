package io.github.Eduardo_Port.library.controller.mappers;

import io.github.Eduardo_Port.library.controller.dto.AuthorDTO;
import io.github.Eduardo_Port.library.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(AuthorDTO dto);
    AuthorDTO toDTO(Author author);
}
