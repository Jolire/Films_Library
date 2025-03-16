package com.cinema.filmlibrary.service;

import com.cinema.filmlibrary.entity.Film;
import com.cinema.filmlibrary.entity.Review;
import com.cinema.filmlibrary.repository.FilmRepository;
import com.cinema.filmlibrary.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

/*Class that made CRUD operations with Review object. */
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final FilmRepository filmRepository;
    private final FilmService filmService;

    /** Constructor of the class.
     *
     * @param reviewRepository object of the ReviewRepository class
     * @param filmService object of the FilmRepository class
     */
    public ReviewService(ReviewRepository reviewRepository, FilmRepository filmRepository, FilmService filmService) {
        this.reviewRepository = reviewRepository;
        this.filmRepository = filmRepository;
        this.filmService = filmService;
    }

    /** Function to add to the film.
     *
     * @param filmId id of the film
     * @param review object of the Review class
     * @return create a return
     */
    public Review createReview(Long filmId, Review review) {
        Film film = filmService.findById(filmId);
        if (film == null) {
            throw new EntityNotFoundException("Book not found");
        }

        review.setFilm(film);
        return reviewRepository.save(review);
    }

    /** Function to update a review in film
     *
     * @param id unique number of the review
     * @param review object of the Review class
     * @return updated review
     */
    public Review updateReview(Integer id, Review review) {
        if (!reviewRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found");
        }
        Review initialReview = reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Review not found"));
        initialReview.setText(review.getText());
        return reviewRepository.save(initialReview);
    }

    /** Functions to delete a review of the film.
     *
     * @param reviewId unique number of the review
     */
    public void deleteReview(Integer reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    /** Function tp get all reviews of the film
     *
     * @param filmId unique number of the film
     * @return list of reviews of one film
     */
    public List<Review> getReviewsByFilm(Long filmId) {
        return reviewRepository.findByFilmId(filmId);
    }

    /** Function to find all reviews
     *
     * @return list of reviews
     */
    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }
}
