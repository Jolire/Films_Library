package com.cinema.filmlibrary.repository;

import com.cinema.filmlibrary.entity.Film;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Class that represents database containing books. */
@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    /** Function that returns books containing substring "title".
     *
     * @param title название книги
     * @return JSON форму объекта Book
     * */
    List<Film> findByTitleContaining(String title);
}