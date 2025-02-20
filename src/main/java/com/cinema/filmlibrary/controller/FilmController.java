package com.cinema.filmlibrary.controller;

import com.cinema.filmlibrary.entity.Film;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Year;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Контроллер для обработки запросов, связанных с фильмами.
 * Предоставляет эндпоинты для получения информации о фильмах по названию и году выпуска.
 */
@RequestMapping("/films")
@RestController
public class FilmController {

    private final ObjectMapper objectMapper;

    /**
     * Создает экземпляр контроллера с {@link ObjectMapper} для работы с JSON.
     *
     * @param objectMapper объект для сериализации и десериализации данных.
     */
    public FilmController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Получает информацию о фильме по названию и году выпуска, переданным в параметрах запроса.
     *
     * @param title       название фильма (например, "Inception").
     * @param releaseYear год выпуска фильма (например, 2010).
     * @return JSON-объект с данными фильма.
     * @throws ResponseStatusException если название пустое или указан некорректный год.
     */
    @GetMapping
    public JsonNode getFilmByTitleAndYearRequest(@RequestParam String title,
                                                 @RequestParam int releaseYear) {
        return validateAndCreateFilm(title, releaseYear);
    }

    /**
     * Получает информацию о фильме по названию и году выпуска, переданным в URL-параметрах.
     *
     * @param title       название фильма (например, "Inception").
     * @param releaseYear год выпуска фильма (например, 2010).
     * @return JSON-объект с данными фильма.
     * @throws ResponseStatusException если название пустое или указан некорректный год.
     */
    @GetMapping("/{title}/{releaseYear}")
    public JsonNode getFilmByTitleAndYearPath(@PathVariable String title,
                                              @PathVariable int releaseYear) {
        return validateAndCreateFilm(title, releaseYear);
    }

    /**
     * Преобразует переданный объект в JSON.
     *
     * @param obj объект для сериализации.
     * @return JSON-объект.
     * @throws ResponseStatusException если произошла ошибка при сериализации.
     */
    private JsonNode serializeToJson(Object obj) {
        try {
            return objectMapper.valueToTree(obj);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ошибка сериализации объекта в JSON", e);
        }
    }

    /**
     * Проверяет входные данные и создает объект фильма.
     *
     * @param title       название фильма.
     * @param releaseYear год выпуска фильма.
     * @return JSON-объект с данными фильма.
     * @throws ResponseStatusException если указан будущий год или год ранее 1888.
     */
    private JsonNode validateAndCreateFilm(String title, int releaseYear) {
        int currentYear = Year.now().getValue();

        if (releaseYear > currentYear) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Фильм не может быть выпущен в будущем");
        } else if (releaseYear < 1888) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Фильмы не существовали до 1888 года");
        }

        Film film = title.isEmpty() ? new Film(null, releaseYear) : new Film(title, releaseYear);

        return serializeToJson(film);
    }
}