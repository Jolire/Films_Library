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


/** Class that control requests and delegate logic to other classes. **/
@RestController
@RequestMapping("/films")
public class FilmController {

    /** Variable to save BookService object. */
    private final FilmService filmService;

    private final FilmMapper filmMapper;

    /** Constructor that sets bookService variable. */
    public FilmController(FilmService filmService, FilmMapper filmMapper) {
        this.filmService = filmService;
        this.filmMapper = filmMapper;
    }

    /**
     * Function to get books with title containing substring.
     *
     * @param title название книги
     * @return JSON форму объекта Book
     * */
    @GetMapping
    public List<FilmDto> getFilm(@RequestParam(required = false) String title) {
        List<Film> films = filmService.findByTitleContaining(title);
        return films.stream()
                .map(filmMapper::toDto)
                .toList();
    }

    /** Function to get all books from database.
     *
     * @return JSON objects of all books
     */
    @GetMapping("/all")
    public List<FilmDto> getAllFilms() {
        List<Film> films = filmService.findAllFilms();
        return films.stream()
                .map(filmMapper::toDto)
                .toList();
    }

    /**
     * Function that holds Get request and returns book with certain id.
     *
     * @param id идентификатор объекта в базе данных
     * @return JSON форму объекта Book
     * */
    @GetMapping("/{id}")
    public FilmDto getFilmById(@PathVariable Long id) {
        Film film = filmService.findById(id);
        return filmMapper.toDto(film);
    }

    /**
     * Function that holds Post request and save book in database.
     *
     * @param film JSON форма объекта
     * @return JSON форму объекта Book
     * */
    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        return filmService.save(film);
    }

    /**
     * Function that holds Put request and updates book with certain id.
     *
     * @param id идентификатор объекта в базе данных
     * @param film JSON форма объекта
     * @return JSON форму объекта Book
     * */
    @PutMapping("/{id}")
    public Film updateFilm(@PathVariable Long id, @RequestBody Film film) {
        return filmService.update(id, film);
    }

    /** Function that holds Delete request and removes book from database.
     *
     * @param id идентификатор объекта в базе данных
     * */
    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) {
        filmService.delete(id);
    }
}