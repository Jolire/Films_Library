package com.cinema.filmlibrary.exception;

/**
 * Класс {@code ErrorResponse} представляет объект ответа об ошибке.
 */
public class ErrorResponse {

    private int status;
    private String message;

    /**
     * Конструктор для создания объекта {@code ErrorResponse}.
     */
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
