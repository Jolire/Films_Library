package com.cinema.filmlibrary.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Class that represents data transfer object of the Film. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmDto {
    private String title;
    private int releaseYear;
    private String genre;
    private List<DirectorDto> directors;
    private List<ReviewDto> reviews;
}