package com.cinema.filmlibrary.mapper;

import com.cinema.filmlibrary.entity.Director;
import com.cinema.filmlibrary.entity.Review;
import com.cinema.filmlibrary.entity.Film;
import com.cinema.filmlibrary.dto.ReviewDto;
import com.cinema.filmlibrary.dto.DirectorDto;
import com.cinema.filmlibrary.dto.FilmDto;
import org.springframework.stereotype.Component;

import java.util.List;

/** Class for transfer object from dto and vice versa. */
@Component
public class FilmMapper {
    private final DirectorMapper directorMapper;
    private final ReviewMapper reviewMapper;

    /** Constructor of classes. */
    public FilmMapper(DirectorMapper directorMapper, ReviewMapper reviewMapper) {
        this.directorMapper = directorMapper;
        this.reviewMapper = reviewMapper;
    }

    /** Function to transform DTO to object
     *
     * @param film object of Film class
     * @return return DTO object
     */
    public FilmDto toDto(Film film){
        FilmDto filmDto = new FilmDto();
        filmDto.setTitle(film.getTitle());

        if (film.getDirectors() != null) {
            List<DirectorDto> directorDto = film.getDirectors().stream()
                    .map(directorMapper::toDto)
                    .toList();
            filmDto.setDirectors(directorDto);
        }

        if (film.getReviews() != null) {
            List<ReviewDto> reviewDto = film.getReviews().stream()
                    .map(reviewMapper::toDto)
                    .toList();
            filmDto.setReviews(reviewDto);
        }

        return filmDto;
    }

    public Film toEntity(FilmDto filmDto){
        Film film = new Film();
        film.setTitle(filmDto.getTitle());
        if (filmDto.getDirectors() != null) {
            List<Director> directors = filmDto.getDirectors().stream()
                    .map(directorMapper :: toEntity)
                    .toList();
            film.setDirectors(directors);
        }

        if (filmDto.getReviews() != null) {
            List<Review> reviews = filmDto.getReviews().stream()
                    .map(reviewMapper :: toEntity)
                    .toList();
            film.setReviews(reviews);
        }
        return film;
    }
}
