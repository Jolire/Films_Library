package com.cinema.filmlibrary.repository;

import com.cinema.filmlibrary.entity.Film;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Class represents database containing films. */
@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    /** Function that returns Films containing substring "title".
     *
     * @param title of the Film
     * @return JSON form of Film object
     * */

    List <Film> findByTitleContaining(String title);
}