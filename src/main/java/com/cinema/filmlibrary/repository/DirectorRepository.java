package com.cinema.filmlibrary.repository;

import com.cinema.filmlibrary.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Class represent database contains directors*/
@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    /** Functions to find Director bi name.
     *
     * @param name - name of class Director
     * @return object of class Director
     */
    Director findByName(String name);

    /** Function to check existence of object in database by name.
     *
     * @param name name pf the Director
     * @return true if Director exists, false otherwise
     */
    boolean existsByName(String name);
}
