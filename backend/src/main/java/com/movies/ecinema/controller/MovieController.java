package com.movies.ecinema.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.movies.ecinema.dto.MovieDto;
import com.movies.ecinema.entity.Movie;
import com.movies.ecinema.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private ModelMapper modelMapper;

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    
    // Add a new movie
    @PostMapping
    public ResponseEntity<MovieDto> addMovie(@RequestBody MovieDto movieDto) {
        // Convert DTO to entity
        Movie movieRequest = modelMapper.map(movieDto, Movie.class);
        Movie savedMovie = movieService.addMovie(movieRequest);

        // Convert entity back to DTO before returning
        MovieDto movieResponse = modelMapper.map(savedMovie, MovieDto.class);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    // Get all movies
    @GetMapping
    public List<MovieDto> getAllMovies() {
        return movieService.getAllMovies().stream()
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .collect(Collectors.toList());
    }
}
