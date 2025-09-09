package io.github.Eduardo_Port.library.controller;

import io.github.Eduardo_Port.library.controller.dto.RegisterBookDTO;
import io.github.Eduardo_Port.library.controller.dto.ResponseError;
import io.github.Eduardo_Port.library.exceptions.DuplicatedRegisterException;
import io.github.Eduardo_Port.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid RegisterBookDTO dto ) {
        try {
            return ResponseEntity.ok(dto);
        } catch (DuplicatedRegisterException e) {
            var error = ResponseError.conflictResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
}
