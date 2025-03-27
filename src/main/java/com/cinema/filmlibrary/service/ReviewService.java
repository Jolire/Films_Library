package com.cinema.filmlibrary.service;

import com.cinema.filmlibrary.entity.Film;
import com.cinema.filmlibrary.entity.Review;
import com.cinema.filmlibrary.repository.FilmRepository;
import com.cinema.filmlibrary.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/** Service for performing CRUD operations with Review entities. */
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final FilmService filmService;
    private final Map<Long, List<Review>> reviewCacheId;
    private final FilmRepository filmRepository;
    private final Map<Long, Film> filmCacheId;

    /** Constructor for ReviewService. */
    public ReviewService(ReviewRepository reviewRepository,
                         FilmService filmService,
                         Map<Long, List<Review>> reviewCacheId,
                         FilmRepository filmRepository,
                         Map<Long, Film> filmCacheId) {
        this.reviewRepository = reviewRepository;
        this.filmService = filmService;
        this.reviewCacheId = reviewCacheId;
        this.filmRepository = filmRepository;
        this.filmCacheId = filmCacheId;
    }

    /** Creates a new review for a film. */
    public Review createReview(Long filmId, Review review) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException("Film not found"));
        review.setFilm(film);

        // Update cache
        Film cachedFilm = filmCacheId.get(filmId);
        if (cachedFilm != null) {
            List<Review> reviews = cachedFilm.getReviews();
            reviews.add(review);
            cachedFilm.setReviews(reviews);
            filmCacheId.put(filmId, cachedFilm);
        }

        Review savedReview = reviewRepository.save(review);

        if (reviewCacheId.containsKey(filmId)) {
            List<Review> reviews = reviewCacheId.get(filmId);
            reviews.add(savedReview);
            reviewCacheId.put(filmId, reviews);
        }

        return savedReview;
    }

    /** Updates an existing review. */
    public Review updateReview(int reviewId, Review review, Long filmId) {
        Film film = filmService.findById(filmId);
        if (film == null) {
            film = filmRepository.findById(filmId)
                    .orElseThrow(() -> new EntityNotFoundException("Film not found"));
        }

        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        if (!film.getReviews().contains(existingReview)) {
            throw new EntityNotFoundException("Review not found for this film");
        }

        existingReview.setMessage(review.getMessage());
        existingReview.setRating(review.getRating());

        Review updatedReview = reviewRepository.save(existingReview);

        // Update caches
        List<Review> cachedReviews = reviewCacheId.get(filmId);
        if (cachedReviews != null) {
            cachedReviews.removeIf(rev -> rev.getId().equals(reviewId));
            cachedReviews.add(updatedReview);
            reviewCacheId.put(filmId, cachedReviews);
        }

        Film cachedFilm = filmCacheId.get(filmId);
        if (cachedFilm != null) {
            if (cachedReviews == null) {
                cachedReviews = cachedFilm.getReviews();
                cachedReviews.removeIf(rev -> rev.getId().equals(reviewId));
                cachedReviews.add(updatedReview);
            }
            cachedFilm.setReviews(cachedReviews);
            filmCacheId.put(filmId, cachedFilm);
        }

        return updatedReview;
    }

    /** Deletes a review. */
    public void deleteReview(int reviewId, Long filmId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new EntityNotFoundException("Review not found");
        }

        reviewRepository.deleteById(reviewId);

        // Update caches
        List<Review> reviews = reviewCacheId.get(filmId);
        if (reviews != null) {
            reviews.removeIf(rev -> rev.getId().equals(reviewId));
            reviewCacheId.put(filmId, reviews);
        }

        Film film = filmCacheId.get(filmId);
        if (film != null) {
            if (reviews == null) {
                reviews = film.getReviews();
                reviews.removeIf(rev -> rev.getId().equals(reviewId));
            }
            film.setReviews(reviews);
            filmCacheId.put(filmId, film);
        }
    }

    /** Gets all reviews for a specific film. */
    public List<Review> getReviewsByFilmId(Long filmId) {
        List<Review> reviews = reviewCacheId.get(filmId);
        if (reviews != null) {
            System.out.println("Reviews retrieved from cache");
            return reviews;
        }

        reviews = reviewRepository.findByFilmId(filmId);
        reviewCacheId.put(filmId, reviews);
        return reviews;
    }

    /** Gets all reviews from the database. */
    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }
}