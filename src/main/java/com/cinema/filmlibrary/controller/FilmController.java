package com.cinema.filmlibrary.controller;

import com.cinema.filmlibrary.entity.Film;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Контроллер для обработки запросов, связанных с фильмами.
 */
@RequestMapping("/films")
@RestController
public class FilmController {

    private final List<Film> filmDatabase = new ArrayList<>();

    /**
     * Получает список фильмов по параметрам запроса (title, releaseYear).
     * Можно передавать один или оба параметра.
     */
    public FilmController() {
        filmDatabase.add(new Film("Inception", 2010));
        filmDatabase.add(new Film("Interstellar", 2014));
        filmDatabase.add(new Film("Dune", 2021));
        filmDatabase.add(new Film("Titanic", 1997));
    }

    /**
     * Получает список фильмов по параметрам запроса (title, releaseYear).
     * Можно передавать один или оба параметра.
     *
     * @param title название фильма (необязательный параметр)
     * @param releaseYear год выпуска фильма (необязательный параметр)
     * @return список фильмов, соответствующих запросу
     */

    @GetMapping
    public ResponseEntity<List<Film>> getFilms(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer releaseYear) {

        List<Film> filteredFilms = new ArrayList<>();

        for (Film film : filmDatabase) {
            boolean matchesTitle = (title == null || film.getTitle().equalsIgnoreCase(title));
            boolean matchesYear = (releaseYear == null || film.getReleaseYear() == releaseYear);

            if (matchesTitle && matchesYear) {
                filteredFilms.add(film);
            }
        }

        if (filteredFilms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(filteredFilms);
        }

        return ResponseEntity.ok(filteredFilms);
    }

    /**
     * Получает фильм по параметрам пути (title, releaseYear).
     */
    @GetMapping("/{title}/{releaseYear}")
    public ResponseEntity<Film> getFilmByPath(
            @PathVariable String title,
            @PathVariable int releaseYear) {

        // Поиск фильма по параметрам пути
        for (Film film : filmDatabase) {
            if (film.getTitle().equalsIgnoreCase(title) && film.getReleaseYear() == releaseYear) {
                return ResponseEntity.ok(film);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Film("Фильм не найден", releaseYear));
    }
}