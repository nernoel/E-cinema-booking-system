package com.movies.ecinema.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.movies.ecinema.entity.Movie;
import com.movies.ecinema.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    // Get all movies from JPA repository
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    // Save movie to JPA repository
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }
    
}
