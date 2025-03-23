package com.cinema.filmlibrary.repository;

import com.cinema.filmlibrary.entity.Film;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/** Class that represents database containing films. */
@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    /** Function that returns films containing substring "title".
     *
     * @param title название фильма
     * @return список фильмов, содержащих указанную подстроку в названии
     */
    List<Film> findByTitleContaining(String title);
}