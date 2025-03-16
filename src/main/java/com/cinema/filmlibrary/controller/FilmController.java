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


/** Class that control requests and delegate logic to other classes. */
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;
    private final FilmMapper filmMapper;

    /** Constructor that sets filmLibrary variable. */
    public FilmController(FilmService filmService, FilmMapper filmMapper) {
        this.filmService = filmService;
        this.filmMapper = filmMapper;
    }
    /** Functions to get film with title.
     *
     * @param title name of the film
     * @return JSON form of the Film object
     */
    @GetMapping
    public List<FilmDto> getFilms(@RequestParam(required = false) String title) {
        List<Film> films = filmService.findByTitleContaining(title);
        return films.stream()
                .map(filmMapper :: toDto)
                .toList();
    }

    /** Function to get all film from db.
     *
     * @return JSON form of the all Film object
     */
    @GetMapping("/all")
    public List<FilmDto> getAllFilms() {
        List<Film> films = filmService.findAllFilms();
        return films.stream()
                .map(filmMapper :: toDto)
                .toList();
    }

    /** Function to get film by id
     *
      * @param id unique number of film
     * @return JSON form of the Film object
     */
    @GetMapping("/{id}")
    public FilmDto getFilmById(@PathVariable long id) {
        Film film = filmService.findById(id);
        return filmMapper.toDto(film);
    }

    /** Function to create film
     *
     * @param film JSON form of object
     * @return JSON form of object Film
     */
    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        return filmService.save(film);
    }

    /** Function to update film
     *
     * @param id unique number of film
     * @param film JSON form of object
     * @return form of object Film
     */
    @PutMapping("/{id}")
    public Film updateFilm(@PathVariable long id, @RequestBody Film film) {
        return filmService.update(id, film);
    }

    /** Function to delete a film
     *
     * @param id unique number of film
     */
    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) {
        filmService.delete(id);
    }

}