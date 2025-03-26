package com.cinema.filmlibrary.service;

import com.cinema.filmlibrary.entity.Film;
import com.cinema.filmlibrary.entity.Review;
import com.cinema.filmlibrary.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

/** Class that make CRUD operations with Review object. */
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final FilmService filmService;

    /** Constructor of the class.
     *
     * @param reviewRepository object of the ReviewRepository class
     * @param filmService object of the BookRepository class
     */
    public ReviewService(ReviewRepository reviewRepository, FilmService filmService) {
        this.reviewRepository = reviewRepository;
        this.filmService = filmService;
    }

    /** Function to add review to the book.
     *
     * @param filmId id of the book
     * @param review object of the Review class
     * @return created review
     */
    public Review createReview(Long filmId, Review review) {
        Film film = filmService.findById(filmId);
        if (film == null) {
            throw new EntityNotFoundException("Film not found");
        }

        review.setFilm(film);
        return reviewRepository.save(review);
    }

    /** Function to update review of the book.
     *
     * @param id id of the review
     * @param review object of the Review class
     * @return updated review
     */
    public Review updateReview(Integer id, Review review) {
        if (!reviewRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found");
        }
        Review initialReview = reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Review not found"));
        initialReview.setMessage(review.getMessage());
        return reviewRepository.save(initialReview);
    }

    /** Function to delete review.
     *
     * @param reviewId id of the review
     */
    public void deleteReview(Integer reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    /** Function to get all reviews of the book.
     *
     * @param filmId id of the book
     * @return reviews of the book
     */
    public List<Review> getReviewsByFilmId(Long filmId) {

        return reviewRepository.findFilmById(filmId);
    }

    /** Function to get all reviews from database.
     *
     * @return list of reviews
     */
    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }
}