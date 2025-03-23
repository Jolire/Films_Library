package com.cinema.filmlibrary.service;

import com.cinema.filmlibrary.entity.Director;
import com.cinema.filmlibrary.entity.Film;
import com.cinema.filmlibrary.entity.Review;
import com.cinema.filmlibrary.repository.DirectorRepository;
import com.cinema.filmlibrary.repository.FilmRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/** Class to store business logic of the app. */
@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final DirectorRepository directorRepository;

    /**
     * Constructor to set filmRepository variable.
     *
     * @param filmRepository объект класса FilmRepository
     * @param directorRepository объект класса DirectorRepository
     */
    public FilmService(FilmRepository filmRepository, DirectorRepository directorRepository) {
        this.filmRepository = filmRepository;
        this.directorRepository = directorRepository;
    }

    /** Function that returns films which contain substring "title".
     *
     * @param title название фильма
     * @return список фильмов, содержащих указанную подстроку в названии
     */
    public List<Film> findByTitleContaining(String title) {
        return filmRepository.findByTitleContaining(title);
    }

    /** Function to get all films from database.
     *
     * @return все фильмы в базе данных
     */
    public List<Film> findAllFilms() {
        return filmRepository.findAll();
    }

    /** Function that returns film with certain id.
     *
     * @param id идентификатор фильма в базе данных
     * @return объект класса Film
     */
    public Film findById(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Film not found"));
    }

    /** Function that saves film in database.
     *
     * @param film объект класса Film
     * @return объект класса Film
     */
    public Film save(Film film) {

        if (film.getDirectors() != null) {
            List<Director> savedDirectors = new ArrayList<>();

            for (Director director : film.getDirectors()) {

                if (directorRepository.existsByName(director.getName())) {
                    Director existingDirector = directorRepository.findByName(director.getName());
                    savedDirectors.add(existingDirector);
                } else {
                    savedDirectors.add(directorRepository.save(director));
                }
            }

            film.setDirectors(savedDirectors);
        }

        for (Review review : film.getReviews()) {
            review.setFilm(film);
        }

        return filmRepository.save(film);
    }

    /** Function that updates info about film.
     *
     * @param id идентификатор фильма
     * @param film объект класса Film
     * @return объект класса Film
     */
    public Film update(Long id, Film film) {
        if (!filmRepository.existsById(id)) {
            throw new EntityNotFoundException("Film not found");
        }
        film.setId(id);
        return filmRepository.save(film);
    }

    /**
     * Function that removes film from database.
     *
     * @param id идентификатор объекта в базе данных
     */
    public void delete(Long id) {
        filmRepository.deleteById(id);
    }
}