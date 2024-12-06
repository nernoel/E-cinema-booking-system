package ecinema.booking.system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ecinema.booking.system.dto.MovieDto;
import ecinema.booking.system.repository.MovieRepository;
import ecinema.booking.system.service.MovieService;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    private final MovieRepository movieRepository;

    public MovieController(MovieService movieService, MovieRepository movieRepository) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    // Create a new movie
    @PostMapping("/create-movie")
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto) {
        MovieDto newMovie = movieService.createMovie(movieDto);
        return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
    }

    // Get all movies
    @GetMapping("/get-movies")
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        List<MovieDto> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    // Get movies by genre
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MovieDto>> getMoviesByGenre(@PathVariable String genre) {
        List<MovieDto> movies = movieService.getMoviesByGenre(genre);
        return ResponseEntity.ok(movies);
    }

    // Get a specific movie by its ID
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        MovieDto movie = movieService.getMovieById(id); // Service layer handles finding the movie
        if (movie != null) {
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}