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
     * Constructor to set bookRepository variable.
     *
     * @param filmRepository объект класса BookRepository
     * */
    public FilmService(FilmRepository filmRepository, DirectorRepository directorRepository) {
        this.filmRepository = filmRepository;
        this.directorRepository = directorRepository;
    }

    /** Function that returns books which contains substring "title".
     *
     * @param title название книги
     * @return JSON форму объекта Book
     * */
    public List<Film> findByTitleContaining(String title) {
        return filmRepository.findByTitleContaining(title);
    }

    /** Function to get all books from database.
     *
     * @return all books in database
     */
    public List<Film> findAllFilms() {
        return filmRepository.findAll();
    }

    /** Function that returns book with certain id.
     *
     * @param id идентификатор книги в базе данных
     * @return JSON форму объекта Book
     * */
    public Film findById(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    /** Function that saves book in database.
     *
     * @param film объек класса Book
     * @return JSON форму объекта Book
     * */
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

    /** Function that updates info about book.
     *
     * @param id идентификатор книги
     * @param film объект класса Book
     * @return JSON форму объекта Book
     * */
    public Film update(Long id, Film film ) {
        if (!filmRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found");
        }
        film.setId(id);
        return filmRepository.save(film);
    }

    /**
     * Function that removes book from database.
     *
     * @param id идентификатор объекта в базе данных
     * */
    public void delete(Long id) {
        filmRepository.deleteById(id);

    }
}
