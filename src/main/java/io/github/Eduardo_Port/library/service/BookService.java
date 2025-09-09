package io.github.Eduardo_Port.library.service;

import io.github.Eduardo_Port.library.controller.dto.RegisterBookDTO;
import io.github.Eduardo_Port.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    public void save(RegisterBookDTO bookDTO) {
        System.out.println("oi");
    }
}
