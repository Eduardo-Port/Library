package io.github.Eduardo_Port.library.controller;

import io.github.Eduardo_Port.library.controller.dto.RegisterBookDTO;
import io.github.Eduardo_Port.library.controller.dto.ResultSearchBookDTO;
import io.github.Eduardo_Port.library.controller.mappers.BookMapper;
import io.github.Eduardo_Port.library.model.Book;
import io.github.Eduardo_Port.library.model.GenreBook;
import io.github.Eduardo_Port.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<List<ResultSearchBookDTO>> search(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "title", required = false)
            String title,
            @RequestParam(value = "author-name", required = false)
            String authorName,
            @RequestParam(value = "genre", required = false)
            GenreBook genre,
            @RequestParam(value = "publication-date", required = false)
            Integer publicationDate
    ) {
        var result = service.search(isbn, authorName, title, publicationDate, genre);
        var list =result.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody @Valid RegisterBookDTO dto) {
        return service.getById(UUID.fromString(id)).map(book -> {
            Book bookUpdated = mapper.toEntity(dto);
            book.setPublicationDate(bookUpdated.getPublicationDate());
            book.setAuthor(bookUpdated.getAuthor());
            book.setIsbn(bookUpdated.getIsbn());
            book.setGenre(bookUpdated.getGenre());
            book.setPrice(bookUpdated.getPrice());
            book.setTitle(bookUpdated.getTitle());

            service.update(book);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.unprocessableEntity().build());
    }
}
