package io.github.Eduardo_Port.library.controller;

import io.github.Eduardo_Port.library.controller.dto.AuthorDTO;
import io.github.Eduardo_Port.library.controller.dto.ResponseError;
import io.github.Eduardo_Port.library.exceptions.DuplicatedRegisterException;
import io.github.Eduardo_Port.library.exceptions.OperationNotAllowed;
import io.github.Eduardo_Port.library.model.Author;
import io.github.Eduardo_Port.library.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody AuthorDTO author) {
        try {
            var authorEntity = author.toAuthor();
            authorService.save(authorEntity);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(authorEntity.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (DuplicatedRegisterException e) {
            var errorDTO = ResponseError.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable String id) {
        var idAuthor = UUID.fromString(id);
        Optional<Author> author = authorService.findById(idAuthor);
        if(author.isPresent()) {
            Author entity = author.get();
            AuthorDTO authorDTO = new AuthorDTO(entity.getId(), entity.getName(), entity.getDateBirth(), entity.getNationality());
            return ResponseEntity.ok(authorDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        try {
            var idAuthor = UUID.fromString(id);
            Optional<Author> author = authorService.findById(idAuthor);
            if (author.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            authorService.delete(author.get());
            return ResponseEntity.noContent().build();
        } catch (OperationNotAllowed e) {
            var errorDTO = ResponseError.standardResponse(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> findByNameAndNationality(@RequestParam(required = false) String name, @RequestParam(required = false) String nationality) {
        List<Author> result = authorService.search(name, nationality);
        List<AuthorDTO> list = result
                .stream()
                .map(
                author -> new AuthorDTO(
                        author.getId(),
                        author.getName(),
                        author.getDateBirth(),
                        author.getNationality())
                ).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody AuthorDTO authorDTO) {
        try {
            var idAuthor = UUID.fromString(id);
            Optional<Author> author = authorService.findById(idAuthor);
            if (author.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            var authorEntity = author.get();
            authorEntity.update(authorDTO.name(), authorDTO.dateBirth(), authorDTO.nationality());
            authorService.update(authorEntity);

            return ResponseEntity.noContent().build();
        } catch (DuplicatedRegisterException e) {
            var errorDTO = ResponseError.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }
}
