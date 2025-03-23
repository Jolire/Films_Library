package com.cinema.filmlibrary.repository;

import com.cinema.filmlibrary.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Class that represents database containing directors. **/
@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    /** Function to find director by name.
     *
     * @param name - name of the director
     * @return object of class Director
     */
    Director findByName(String name);

    /** Function to check existence of object in database by name.
     *
     * @param name name of the director
     * @return true if director exists, false otherwise
     */
    boolean existsByName(String name);
}