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


/** Controller for handling film-related operations. */
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;
    private final FilmMapper filmMapper;

    /** Constructor for FilmController.
     *
     * @param filmService service for film operations
     * @param filmMapper mapper for converting between Film and FilmDto
     */
    public FilmController(FilmService filmService, FilmMapper filmMapper) {
        this.filmService = filmService;
        this.filmMapper = filmMapper;
    }

    /** Gets films by title containing substring.
     *
     * @param title substring to search in film titles
     * @return FilmDto of the found film
     */
    @GetMapping
    public FilmDto getFilmByTitle(@RequestParam(required = false) String title) {
        Film film = filmService.findByTitle(title);
        return filmMapper.toDto(film);
    }

    /** Gets all films from database.
     *
     * @return list of all FilmDtos
     */
    @GetMapping("/all")
    public List<FilmDto> getAllFilms() {
        List<Film> films = filmService.findAllFilms();
        return films.stream()
                .map(filmMapper::toDto)
                .toList();
    }

    /** Gets film by ID.
     *
     * @param id ID of the film
     * @return FilmDto of the requested film
     */
    @GetMapping("/{id}")
    public FilmDto getFilmById(@PathVariable Long id) {
        Film film = filmService.findById(id);
        return filmMapper.toDto(film);
    }

    /** Gets films by director's name.
     *
     * @param directorName name of the director
     * @return list of FilmDtos by specified director
     */
    @GetMapping("/find")
    public List<FilmDto> getBooksByAuthorName(@RequestParam(required = false) String directorName) {
        return filmService.findByDirectorName(directorName).stream()
                .map(filmMapper::toDto)
                .toList();
    }

    /** Function to get books with review amount greater than reviewCount.
     *
     * @param reviewCount amount of reviews
     * @return list of books
     */
    @GetMapping("/find/reviews")
    public List<FilmDto> getBooksByReviewCount(@RequestParam(required = false) Long reviewCount) {
        return filmService.findByReviewCount(reviewCount).stream()
                .map(filmMapper::toDto)
                .toList();
    }

    /** Creates a new film.
     *
     * @param film Film object to create
     * @return created Film object
     */
    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        return filmService.save(film);
    }

    /** Updates an existing film.
     *
     * @param id ID of the film to update
     * @param film updated Film data
     * @return updated Film object
     */
    @PutMapping("/{id}")
    public Film updateFilm(@PathVariable Long id, @RequestBody Film film) {
        return filmService.update(id, film);
    }

    /** Deletes a film.
     *
     * @param id ID of the film to delete
     */
    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) {
        filmService.delete(id);
    }
}