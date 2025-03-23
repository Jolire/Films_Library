package com.cinema.filmlibrary.controller;

import com.cinema.filmlibrary.dto.FilmDto;
import com.cinema.filmlibrary.entity.Film;
import com.cinema.filmlibrary.mapper.FilmMapper;
import com.cinema.filmlibrary.service.FilmService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/** Class that controls requests and delegates logic to other classes. */
@RestController
@RequestMapping("/films")
public class FilmController {

    /** Variable to save FilmService object. */
    private final FilmService filmService;

    private final FilmMapper filmMapper;

    /** Constructor that sets filmService variable. */
    public FilmController(FilmService filmService, FilmMapper filmMapper) {
        this.filmService = filmService;
        this.filmMapper = filmMapper;
    }

    /**
     * Function to get films with title containing substring.
     *
     * @param title название фильма
     * @return список фильмов, содержащих указанную подстроку в названии
     */
    @GetMapping
    public List<FilmDto> getFilms(@RequestParam(required = false) String title) {
        List<Film> films = filmService.findByTitleContaining(title);
        return films.stream()
                .map(filmMapper::toDto)
                .toList();
    }

    /** Function to get all films from database.
     *
     * @return список всех фильмов
     */
    @GetMapping("/all")
    public List<FilmDto> getAllFilms() {
        List<Film> films = filmService.findAllFilms();
        return films.stream()
                .map(filmMapper::toDto)
                .toList();
    }

    /**
     * Function that holds Get request and returns film with certain id.
     *
     * @param id идентификатор фильма в базе данных
     * @return объект класса FilmDto
     */
    @GetMapping("/{id}")
    public FilmDto getFilmById(@PathVariable Long id) {
        Film film = filmService.findById(id);
        return filmMapper.toDto(film);
    }

    /**
     * Function that holds Post request and saves film in database.
     *
     * @param film объект класса Film
     * @return сохраненный объект класса Film
     */
    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        return filmService.save(film);
    }

    /**
     * Function that holds Put request and updates film with certain id.
     *
     * @param id идентификатор фильма в базе данных
     * @param film объект класса Film
     * @return обновленный объект класса Film
     */
    @PutMapping("/{id}")
    public Film updateFilm(@PathVariable Long id, @RequestBody Film film) {
        return filmService.update(id, film);
    }

    /** Function that holds Delete request and removes film from database.
     *
     * @param id идентификатор фильма в базе данных
     */
    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) {
        filmService.delete(id);
    }
}