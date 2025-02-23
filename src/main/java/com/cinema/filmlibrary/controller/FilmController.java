package com.cinema.filmlibrary.controller;

import com.cinema.filmlibrary.entity.Film;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с фильмами.
 */
@RequestMapping("/films")
@RestController
public class FilmController {

    private final List<Film> filmDatabase = new ArrayList<>();

    public FilmController() {
        filmDatabase.add(new Film("Inception", 2010));
        filmDatabase.add(new Film("Interstellar", 2014));
        filmDatabase.add(new Film("Dune", 2021));
        filmDatabase.add(new Film("Titanic", 1997));
    }

    /**
     * Получает список фильмов по названию и/или году выпуска.
     * Можно передавать один или оба параметра.
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

}
