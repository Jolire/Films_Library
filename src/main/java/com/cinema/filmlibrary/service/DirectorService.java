package com.cinema.filmlibrary.service;

import com.cinema.filmlibrary.entity.Director;
import com.cinema.filmlibrary.entity.Film;
import com.cinema.filmlibrary.repository.DirectorRepository;
import com.cinema.filmlibrary.repository.FilmRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/** Class to store business logic of the app. */
@Service
public class DirectorService {

    private static final String ERROR_MESSAGE = "Director not found";

    private final DirectorRepository directorRepository;
    public final FilmService filmService;
    public final FilmRepository filmRepository;

    /** Constructor to set directorRepository variable. */
    public DirectorService(DirectorRepository directorRepository, FilmService filmService, FilmRepository filmRepository) {
        this.directorRepository = directorRepository;
        this.filmService = filmService;
        this.filmRepository = filmRepository;
    }

    /** Function that returns director with certain id.
     *
     * @param id unique number of object in db
     * @return JSON form of Director object
     * */

    public Director findById(Long id, Long filmId) {
        if (!filmRepository.existsById(filmId)) {
            throw new EntityNotFoundException(ERROR_MESSAGE);
        }

        Film film = filmService.findById(filmId);
        List<Director> directors = film.getDirectors();
        Director director = directorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE));
        if (directors.contains(director)) {
            return director;
        } else{
            throw new EntityNotFoundException(ERROR_MESSAGE);
        }
    }

    /** Function to get all directors from database.
     *
     * @return list pf directors
     */
    public List<Director> findAllDirectors() {
        return directorRepository.findAll();
    }

    /** Function that save director in database.
     *
     * @param director object of Director class
     * @return JSON form of Director object
     * */
    public Director save(Director director,Long filmId) {

        Film film = filmService.findById(filmId);

        if (film == null) {
            throw new EntityNotFoundException("Film not found");
        }
        List<Film> newFilms = new ArrayList<>();

        if (directorRepository.existsByName(director.getName())) {
            director = directorRepository.findByName(director.getName());
            newFilms = director.getFilms();
        }

        List<Director> directors = film.getDirectors();
        if (!directors.contains(director)) {
            directors.add(director);
            film.setDirectors(directors);
            newFilms.add(film);
            director.setFilms(newFilms);
        }

        return directorRepository.save(director);
    }

    /** Function that updates info about director with certain id.
     *
     * @param id unique number of directors in db
     * @param director object of Director class
     * @return JSON форму объекта Author
     * */
    public Director update(Long id, Director director) {
        if (!directorRepository.existsById(id)) {
            throw new EntityNotFoundException(ERROR_MESSAGE);
        }
        director.setId(id);
        return directorRepository.save(director);
    }

    /** Function that deletes director with certain id. */
    public void delete(Long id, Long filmId) {

        Film film = filmService.findById(filmId);
        List<Director> directors = film.getDirectors();
        Director director = findById(id, filmId);

        directors.remove(director);

        film.setDirectors(directors);
        filmService.update(filmId, film);

        List<Film> films = director.getFilms();
        films.remove(film);
        if (films.isEmpty()) {
            directorRepository.delete(director);
        } else {
            director.setFilms(films);
            update(id, director);
        }
    }
}
