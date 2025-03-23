package com.cinema.filmlibrary.controller;

import com.cinema.filmlibrary.entity.Director;
import com.cinema.filmlibrary.service.DirectorService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/** Class that controls requests and delegates logic to other classes. */
@RestController
@RequestMapping("/films/{filmId}/directors")
public class DirectorController {
    private final DirectorService directorService;

    /** Constructor of the class.
     *
     * @param directorService - object of DirectorService class
     */
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    /** Function to add director to the film.
     *
     * @param director object of the Director class
     * @param filmId id of the film
     * @return created director
     */
    @PostMapping
    public Director createDirector(@RequestBody Director director, @PathVariable Long filmId) {
        return directorService.save(director, filmId);
    }

    /** Function to update director.
     *
     * @param directorId - id of the director
     * @param director - object of the Director class
     * @return updated director
     */
    @PutMapping("/{directorId}")
    public Director updateDirector(@PathVariable Long directorId, @RequestBody Director director) {
        return directorService.update(directorId, director);
    }

    /** Function to delete director.
     *
     * @param directorId id of the director
     * @param filmId id of the film
     */
    @DeleteMapping("/{directorId}")
    public void deleteDirector(@PathVariable Long directorId, @PathVariable Long filmId) {
        directorService.delete(directorId, filmId);
    }

    /** Function to get director of the film.
     *
     * @param directorId - id of the director
     * @param filmId - id of the film
     * @return director of the film
     */
    @GetMapping("/{directorId}")
    public Director findByFilmId(@PathVariable Long directorId, @PathVariable Long filmId) {
        return directorService.findById(directorId, filmId);
    }

    /** Function to get all directors from database.
     *
     * @return list of directors
     */
    @GetMapping("/all")
    public List<Director> findAllDirectors() {
        return directorService.findAllDirectors();
    }
}