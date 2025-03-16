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

/** Class control requests and delegate logic to other classes. */
@RestController
@RequestMapping("films/{filmId}/directors")
public class DirectorController {
    private final DirectorService directorService;

    /** Constructor of the class
     *
     * @param directorService object of Director class
     */
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    /** Function to add director to the film.
     *
     * @param director object of the Director class
     * @param filmId unique number of film
     * @return created director
     */
    @PostMapping
    public Director createDirector(@RequestBody Director director, @PathVariable long filmId) {
        return directorService.save(director, filmId);
    }

    /** Function to update director on the film.
     *
     * @param directorId unique number of director
     * @param director object of the Director class
     * @return updated director
     */
    @PutMapping("/{directorId}")
    public Director updateDirector(@PathVariable long directorId, @RequestBody Director director) {
        return directorService.save(director, directorId);
    }

    /** Function to delete director on the film.
     *
     * @param directorId unique number of director
     * @param filmId director object of the Director class
     */
    @DeleteMapping("/{directorId}")
    public void deleteDirector(@PathVariable long directorId, @PathVariable long filmId) {
        directorService.delete(directorId, filmId);
    }

    /** Function to get director of the film.
     *
     * @param directorId unique number of director
     * @param filmId unique number of film
     * @return return director
     */
    @GetMapping("/{directorId}")
    public Director findByFilmId(@PathVariable long directorId, @PathVariable long filmId) {
        return directorService.findById(directorId, filmId);
    }

    /** Function to get all directors
     *
     * @return list of directors
     */
    @GetMapping("/all")
    public List<Director> findAll() {
        return directorService.findAllDirectors();
    }
}