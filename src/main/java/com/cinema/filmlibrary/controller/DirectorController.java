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

/** Controller for handling director-related operations for films. */
@RestController
@RequestMapping("/films/{filmId}/directors")
public class DirectorController {
    private final DirectorService directorService;

    /** Constructor for DirectorController.
     *
     * @param directorService service for director operations
     */
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    /** Adds a director to the specified film.
     *
     * @param director Director object to add
     * @param filmId ID of the film
     * @return created director
     */
    @PostMapping
    public Director addDirector(@RequestBody Director director, @PathVariable Long filmId) {
        return directorService.save(director, filmId);
    }

    /** Updates director information.
     *
     * @param directorId ID of the director to update
     * @param director Updated director data
     * @return updated director
     */
    @PutMapping("/{directorId}")
    public Director updateDirector(
            @PathVariable Long directorId,
            @RequestBody Director director) {
        return directorService.update(directorId, director);
    }

    /** Removes a director from a film or deletes them if no other films exist.
     *
     * @param directorId ID of the director to remove
     * @param filmId ID of the film to remove the director from
     */
    @DeleteMapping("/{directorId}")
    public void removeDirector(
            @PathVariable Long directorId,
            @PathVariable Long filmId) {
        directorService.delete(directorId, filmId);
    }

    /** Gets a director who worked on the specified film.
     *
     * @param directorId ID of the director
     * @param filmId ID of the film to verify association
     * @return director information
     */
    @GetMapping("/{directorId}")
    public Director getFilmDirector(
            @PathVariable Long directorId,
            @PathVariable Long filmId) {
        return directorService.findById(directorId, filmId);
    }

    /** Gets all directors from the database.
     *
     * @return list of all directors
     */
    @GetMapping("/all")
    public List<Director> findAllDirectors() {
        return directorService.findAllDirectors();
    }
}