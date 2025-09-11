package io.github.Eduardo_Port.library.controller.common;

import io.github.Eduardo_Port.library.controller.dto.ResponseError;
import io.github.Eduardo_Port.library.exceptions.DuplicatedRegisterException;
import io.github.Eduardo_Port.library.exceptions.InvalidFieldException;
import io.github.Eduardo_Port.library.exceptions.OperationNotAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<io.github.Eduardo_Port.library.controller.dto.FieldError> listErrors = fieldErrors
                .stream()
                .map(fe ->
                        new io.github.Eduardo_Port.library.controller.dto.FieldError(fe.getField(),
                                fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error",
                listErrors);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicatedRegisterException.class)
    public ResponseError handleDuplicatedRegisterException(DuplicatedRegisterException e) {
        return ResponseError.conflictResponse(e.getMessage());
    }

    @ExceptionHandler(OperationNotAllowed.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleOperationNotAllowedException(OperationNotAllowed e) {
        return ResponseError.conflictResponse(e.getMessage());
    }

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleInvalidFieldException(InvalidFieldException e) {
        return new ResponseError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error",
                List.of(new io.github.Eduardo_Port.library.controller.dto.FieldError(e.getField(), e.getMessage())));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleErrorNotManipulated(RuntimeException e) {
        return new ResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred. Please contact management.",
                List.of());
    }


}
