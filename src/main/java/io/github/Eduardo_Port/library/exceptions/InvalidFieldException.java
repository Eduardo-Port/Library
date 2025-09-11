package io.github.Eduardo_Port.library.exceptions;

import lombok.Getter;

@Getter
public class InvalidFieldException extends RuntimeException {
    private String field;

    public InvalidFieldException(String field, String message) {
        super(message);
        this.field = field;
    }
}
