package com.cinema.filmlibrary.mapper;

import com.cinema.filmlibrary.dto.ReviewDto;
import com.cinema.filmlibrary.entity.Review;
import org.springframework.stereotype.Component;


/** Class to transform object from dto and vice versa. */
@Component
public class ReviewMapper {

    /** Function to transform object to DTO.
     *
     * @param review object of Review class
     * @return DTO object
     */
    public ReviewDto toDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setText(review.getText());
        return reviewDto;
    }

    /** Function to transform DTO to standard object.
     *
     * @param reviewDto object of review DTO class
     * @return stander object
     */
    public Review toEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setText(reviewDto.getText());
        return review;
    }
}
