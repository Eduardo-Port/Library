package io.github.Eduardo_Port.library.controller;

import io.github.Eduardo_Port.library.controller.dto.RegisterBookDTO;
import io.github.Eduardo_Port.library.controller.dto.ResultSearchBookDTO;
import io.github.Eduardo_Port.library.controller.mappers.BookMapper;
import io.github.Eduardo_Port.library.model.Book;
import io.github.Eduardo_Port.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController implements GenericController {
    private final BookService service;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid RegisterBookDTO dto) {
        Book book = mapper.toEntity(dto);
        service.save(book);
        URI location = createHeaderLocation(book.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultSearchBookDTO> findDetails(@PathVariable("id") String id) {
        return service.getById(UUID.fromString(id))
                .map(book -> {
                    ResultSearchBookDTO dto = mapper.toDTO(book);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") String id) {
        return service.getById(UUID.fromString(id)).map(book -> {
            service.delete(book.getId());
            return ResponseEntity.noContent().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
