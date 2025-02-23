package com.cinema.filmlibrary.entity;

/**
 * Класс {@code Film} представляет информацию о фильме.
 * Содержит основные характеристики фильма: название и год выпуска.
 */
public class Film {

    private String title;
    private int releaseYear;

    /**
     * Конструктор без параметров.
     */
    public Film() {
    }

    /**
     * Конструктор с параметрами.
     */
    public Film(String title, int releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}