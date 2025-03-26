package com.cinema.filmlibrary.mapper;

import com.cinema.filmlibrary.dto.DirectorDto;
import com.cinema.filmlibrary.dto.FilmDto;
import com.cinema.filmlibrary.dto.ReviewDto;
import com.cinema.filmlibrary.entity.Director;
import com.cinema.filmlibrary.entity.Film;
import com.cinema.filmlibrary.entity.Review;
import java.util.List;
import org.springframework.stereotype.Component;

/** Class to transform object from dto and vice versa. */
@Component
public class FilmMapper {
    private final DirectorMapper authorMapper;
    private final ReviewMapper reviewMapper;

    /** Constructor of the class. */
    public FilmMapper(DirectorMapper directorMapper, ReviewMapper reviewMapper) {
        this.authorMapper = directorMapper;
        this.reviewMapper = reviewMapper;
    }

    /** Function to transform standard object to DTO.
     *
     * @param film object of the Book class
     * @return dto object
     */
    public FilmDto toDto(Film film) {
        FilmDto filmDto = new FilmDto();
        filmDto.setTitle(film.getTitle());

        if (film.getDirectors() != null) {
            List<DirectorDto> directorDtos = film.getDirectors().stream()
                    .map(authorMapper::toDto)
                    .toList();
            filmDto.setDirectors(directorDtos);
        }

        if (film.getReviews() != null) {
            List<ReviewDto> reviewsDtos = film.getReviews().stream()
                    .map(reviewMapper::toDto)
                    .toList();
            filmDto.setReviews(reviewsDtos);
        }

        return filmDto;
    }

    /** Function to transform DTO to standard object.
     *
     * @param filmDto object of the BookDto class
     * @return standard Book object
     */
    public Film toEntity(FilmDto filmDto) {
        Film film = new Film();
        film.setTitle(filmDto.getTitle());

        if (filmDto.getDirectors() != null) {
            List<Director> directors = filmDto.getDirectors().stream()
                    .map(authorMapper::toEntity)
                    .toList();
            film.setDirectors(directors);
        }

        if (filmDto.getReviews() != null) {
            List<Review> reviews = filmDto.getReviews().stream()
                    .map(reviewMapper::toEntity)
                    .toList();
            film.setReviews(reviews);
        }
        return film;
    }
}