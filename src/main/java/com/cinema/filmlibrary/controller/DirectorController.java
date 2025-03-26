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

/** Class that control requests and delegate logic to other classes. **/
@RestController
@RequestMapping("/films/{filmId}/directors")
public class DirectorController {
    private final DirectorService directorService;

    /** Constructor of the class.
     *
     * @param directorService - object of AuthorService class
     */
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    /** Function to add author to the book.
     *
     * @param director object of the Author class
     * @return created author
     */
    @PostMapping
    public Director createDirector(@RequestBody Director director, @PathVariable Long filmId) {
        return directorService.save(director, filmId);
    }

    /** Function to update review of the book.
     *
     * @param directorId - id of the author
     * @param director - object of the Author class
     * @return updated author
     */
    @PutMapping("/{directorId}")
    public Director updateDirector(@PathVariable Long directorId, @RequestBody Director director) {
        return directorService.update(directorId, director);
    }

    /** Function to delete author.
     *
     * @param directorId id of the author
     */
    @DeleteMapping("/{directorId}")
    public void deleteAuthor(@PathVariable Long directorId, @PathVariable Long filmId) {
        directorService.delete(directorId, filmId);
    }

    /** Function to get author of the book.
     *
     * @param directorId - id of the author
     * @return author of the book
     */
    @GetMapping("/{directorId}")
    public Director findByBookId(@PathVariable Long directorId, @PathVariable Long filmId) {
        return directorService.findById(directorId, filmId);
    }

    /** Function to get all authors from database.
     *
     * @return list of authors
     */
    @GetMapping("/all")
    public List<Director> findAllDirectors() {
        return directorService.findAllDirectors();
    }
}