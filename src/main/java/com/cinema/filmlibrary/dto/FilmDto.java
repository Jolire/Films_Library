package com.cinema.filmlibrary.dto;

import java.util.List;

/** Class that represents data transfer object of the Film. */
public class FilmDto {
    private String title;
    private List<DirectorDto> directors;
    private List<ReviewDto> reviews;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DirectorDto> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorDto> directors) {
        this.directors = directors;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }
}
