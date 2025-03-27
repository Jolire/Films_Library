package com.cinema.filmlibrary.config;

import com.cinema.filmlibrary.entity.Director;
import com.cinema.filmlibrary.entity.Film;
import com.cinema.filmlibrary.entity.Review;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Class to store cache. */
@Configuration
public class Cache {

    /** Bean for book cache (requests by id). */
    @Bean
    public Map<Long, Film> filmCacheId() {
        return new HashMap<>();
    }

    /** Bean for author cache (requests by id). */
    @Bean
    public Map<Long, Director> directorCacheId() {
        return new HashMap<>();
    }

    /** Bean for review cache (requests by id). */
    @Bean
    public Map<Long, List<Review>> reviewCacheId() {
        return new HashMap<>();
    }
}
