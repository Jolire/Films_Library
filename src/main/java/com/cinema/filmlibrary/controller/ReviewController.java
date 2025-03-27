package com.cinema.filmlibrary.controller;

import com.cinema.filmlibrary.dto.ReviewDto;
import com.cinema.filmlibrary.entity.Review;
import com.cinema.filmlibrary.mapper.ReviewMapper;
import com.cinema.filmlibrary.service.ReviewService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling review operations for films.
 */
@RestController
@RequestMapping("/films/{filmId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    /** Constructor for ReviewController.
     *
     * @param reviewService service for review operations
     * @param reviewMapper mapper for converting between Review and ReviewDto
     */
    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    /** Adds a review to the specified film.
     *
     * @param filmId ID of the film
     * @param review Review object to add
     * @return created Review
     */
    @PostMapping
    public Review createReview(@PathVariable Long filmId, @RequestBody Review review) {
        return reviewService.createReview(filmId, review);
    }

    /** Updates an existing review for a film.
     *
     * @param reviewId ID of the review to update
     * @param review Updated review data
     * @param filmId ID of the associated film
     * @return updated Review
     */
    @PutMapping("/{reviewId}")
    public Review updateReview(
            @PathVariable int reviewId,
            @RequestBody Review review,
            @PathVariable Long filmId) {
        return reviewService.updateReview(reviewId, review, filmId);
    }

    /** Deletes a review.
     *
     * @param reviewId ID of the review to delete
     * @param filmId ID of the associated film
     */
    @DeleteMapping("/{reviewId}")
    public void deleteReview(
            @PathVariable int reviewId,
            @PathVariable Long filmId) {
        reviewService.deleteReview(reviewId, filmId);
    }

    /** Gets all reviews from the database.
     *
     * @return list of all reviews
     */
    @GetMapping("/all")
    public List<Review> findAllReviews() {
        return reviewService.findAllReviews();
    }

    /** Gets all reviews for a specific film.
     *
     * @param filmId ID of the film
     * @return list of ReviewDtos for the film
     */
    @GetMapping
    public List<ReviewDto> getReviewsByFilmId(@PathVariable Long filmId) {
        List<Review> reviews = reviewService.getReviewsByFilmId(filmId);
        return reviews.stream()
                .map(reviewMapper::toDto)
                .toList();
    }
}