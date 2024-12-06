package ecinema.booking.system.service;

import java.util.List;

import ecinema.booking.system.dto.MovieDto;


public interface MovieService {

    // Create new movie
    MovieDto createMovie(MovieDto movie);

    // Read all movies
    List<MovieDto> getAllMovies();

    List<MovieDto> getMoviesByGenre(String genre);

    public MovieDto getMovieById(Long id);

}