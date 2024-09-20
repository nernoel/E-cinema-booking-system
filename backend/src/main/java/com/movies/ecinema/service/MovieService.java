package com.movies.ecinema.service;

import java.util.List;
import com.movies.ecinema.entity.Movie;

/*
 * This interface provides service layer methods
 */
public interface MovieService {
    List<Movie> getAllMovies();
    Movie addMovie(Movie movie);
    // void deleteMovie(Long id);
}
