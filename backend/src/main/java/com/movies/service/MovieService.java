package com.movies.service;

import com.movies.dto.MovieDto;

/*
 * This interface provides service layer methods
 */
public interface MovieService {

    MovieDto addMovie(MovieDto movieDto);
    void deleteMovie(Long id);
     
    
}
