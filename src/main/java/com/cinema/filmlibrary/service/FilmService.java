package com.cinema.filmlibrary.service;

import com.cinema.filmlibrary.entity.Director;
import com.cinema.filmlibrary.entity.Film;
import com.cinema.filmlibrary.entity.Review;
import com.cinema.filmlibrary.repository.DirectorRepository;
import com.cinema.filmlibrary.repository.FilmRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/** Class to store business logic related to films. */
@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final DirectorRepository directorRepository;
    private final Map<Long, Film> filmCacheId;
    private final Map<Long, List<Review>> reviewCacheId;
    private final Map<Long, Director> directorCacheId;

    /**
     * Constructor to initialize dependencies.
     *
     * @param filmRepository Film repository
     * @param directorRepository Director repository
     * @param filmCacheId Cache for films
     * @param reviewCacheId Cache for reviews
     * @param directorCacheId Cache for directors
     */
    public FilmService(FilmRepository filmRepository, DirectorRepository directorRepository,
                       Map<Long, Film> filmCacheId, Map<Long, List<Review>> reviewCacheId,
                       Map<Long, Director> directorCacheId) {
        this.filmRepository = filmRepository;
        this.directorRepository = directorRepository;
        this.filmCacheId = filmCacheId;
        this.reviewCacheId = reviewCacheId;
        this.directorCacheId = directorCacheId;
    }

    /** Finds film by title.
     *
     * @param title film title
     * @return Film object
     */
    public Film findByTitle(String title) {
        return filmRepository.findByTitle(title);
    }

    /** Gets all films from database.
     *
     * @return list of all films
     */
    public List<Film> findAllFilms() {
        return filmRepository.findAll();
    }

    /** Finds film by ID with caching.
     *
     * @param id film ID
     * @return Film object
     * @throws EntityNotFoundException if film not found
     */
    public Film findById(Long id) {
        Film cachedFilm = filmCacheId.get(id);
        if (cachedFilm != null) {
            System.out.println("Film by id -  was got from cache");
            return cachedFilm;
        }

        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Film not found"));
        filmCacheId.put(id, film);

        return film;
    }

    /** Find all review many than count. */
    public List<Film> findByReviewCount(Long reviewCount) {
        return filmRepository.findByReviewCount(reviewCount);
    }

    /** Finds films by director's name.
     *
     * @param directorName name of the director
     * @return list of films by specified director
     */
    public List<Film> findByDirectorName(String directorName) {
        return filmRepository.findByDirectorName(directorName);
    }

    /** Saves film in database with proper director and review associations.
     *
     * @param film Film object to save
     * @return saved Film object
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

        if (film.getReviews() != null) {
            for (Review review : film.getReviews()) {
                review.setFilm(film);
            }
        }

        return filmRepository.save(film);
    }

    /** Updates film information.
     *
     * @param id film ID
     * @param film updated Film object
     * @return updated Film object
     * @throws EntityNotFoundException if film not found
     */
    public Film update(Long id, Film film) {
        if (!filmRepository.existsById(id)) {
            throw new EntityNotFoundException("Film not found");
        }

        Film existingFilm = findById(id);
        film.setDirectors(existingFilm.getDirectors());
        film.setReviews(existingFilm.getReviews());

        film.setId(id);

        Film updatedFilm = filmRepository.save(film);
        if (filmCacheId.containsKey(id)) {
            System.out.println("Film was updated in cache");
            filmCacheId.put(id, updatedFilm);
        }

        return updatedFilm;
    }

    /** Deletes film from database and clears related caches.
     *
     * @param id film ID
     */
    public void delete(Long id) {
        reviewCacheId.remove(id);
        filmCacheId.remove(id);
        directorCacheId.clear();
        filmRepository.deleteById(id);
    }

    /** Clears film cache. */
    public void clearCache() {
        System.out.println("Film was delete from cache");
        filmCacheId.clear();
    }

    /** Gets film cache map.
     *
     * @return film cache map
     */
    public Map<Long, Film> getFilmCacheId() {
        return filmCacheId;
    }
}
