package com.cinema.filmlibrary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/**
 * Глобальный обработчик исключений для приложения FilmLibrary.
 * Использует {@code @RestControllerAdvice} для централизованной обработки ошибок.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработка исключения {@link ResponseStatusException}.
     * Возвращает пользователю сообщение об ошибке с соответствующим статусом.
     *
     * @param ex исключение, которое было выброшено
     * @return ответ с информацией об ошибке
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatusCode().value(), ex.getReason());
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    /**
     * Обработка общих исключений (например, RuntimeException).
     * Возвращает ошибку 500 и сообщение об ошибке.
     *
     * @param ex исключение, которое было выброшено
     * @return ответ с кодом 500 и сообщением об ошибке
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}