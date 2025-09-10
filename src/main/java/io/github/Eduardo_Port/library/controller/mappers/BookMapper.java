package io.github.Eduardo_Port.library.controller.mappers;

import io.github.Eduardo_Port.library.controller.dto.RegisterBookDTO;
import io.github.Eduardo_Port.library.controller.dto.ResultSearchBookDTO;
import io.github.Eduardo_Port.library.model.Book;
import io.github.Eduardo_Port.library.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public abstract class BookMapper {
    @Autowired
    AuthorRepository authorRepository;

    @Mapping(expression = "java( authorRepository.findById(dto.authorId()).orElse(null) )", target = "author")
    public abstract Book toEntity(RegisterBookDTO dto);

    public abstract ResultSearchBookDTO toDTO(Book book);

}
