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
     * Constructor to mad=ke filmRepository.
     *
     * @param filmRepository object of Film
     * */

    public FilmService(FilmRepository filmRepository, DirectorRepository directorRepository) {
        this.filmRepository = filmRepository;
        this.directorRepository = directorRepository;
    }

    /** Function that returns Films which have "title".
     *
     * @param title name of the Film
     * @return JSON form of Film object
     * */

    public List<Film>findByTitleContaining(String title){
        return filmRepository.findByTitleContaining(title);
    }

    /** Function to get all books from database.
     *
     * @return all books in database
     */

    public List<Film> findAllFilms(){
        return filmRepository.findAll();
    }

    /** Function that returns film with certain id.
     *
     * @param id the unique number of film
     * @return JSON form of object
     * */
    public Film findById(Long id) {
        return filmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Film not found"));
    }

    /** Function that saves films in db.
     *
     * @param film object of Film class
     * @return JSON form of Film object
     * */
    public Film save(Film film) {

        if (film.getDirectors() != null) {
            List <Director> savedDirectors = new ArrayList<>();

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
     * @param id the unique number of film
     * @param film object of Film class
     * @return JSON form of Film object
     * */
    public Film update(Long id, Film film) {
        if (!filmRepository.existsById(id)) {
            throw new EntityNotFoundException("Film not found");
        }
        film.setId(id);
        return filmRepository.save(film);
    }

    /** Function that removes film from db.
     *
     * @param id unique number of object in db
     */
    public void delete(Long id) {
        filmRepository.deleteById(id);
    }
}
