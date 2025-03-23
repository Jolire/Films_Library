package com.cinema.filmlibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/** Class that represents data transfer object of the Director. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectorDto {
    private String name;
    private String nationality;
    private int birthYear;
}