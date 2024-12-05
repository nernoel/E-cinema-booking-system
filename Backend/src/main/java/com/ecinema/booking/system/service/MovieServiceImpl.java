package ecinema.booking.system.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ecinema.booking.system.dto.MovieDto;
import ecinema.booking.system.entity.Movie;
import ecinema.booking.system.repository.MovieRepository;


import org.modelmapper.ModelMapper;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final ModelMapper modelMapper;

    // All args movie constructor
    public MovieServiceImpl(MovieRepository movieRepository, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
    }

    // Create a new movie
    @Override
    public MovieDto createMovie(MovieDto movieDto) {

        Movie movie = new Movie();

        movie.setTitle(movieDto.getTitle());

        movie.setGenre(movieDto.getGenre());

        movie.setRuntime(movieDto.getRuntime());

        movie.setDescription(movieDto.getDescription());

        movie.setMoviePosterLink(movieDto.getMoviePosterLink());

        movie.setReleaseDate(movieDto.getReleaseDate());

        movie.setCategory(movieDto.getCategory());

        movie.setRating(movieDto.getRating());

        movie.setTrailer(movieDto.getTrailer());

        movie.setDirector(movieDto.getDirector());

        movie.setProducer(movieDto.getProducer());

        movie.setSynopsis(movieDto.getSynopsis());

        movie.setMpaaFilmRatingcode(movieDto.getMpaaFilmRatingcode());

        movie.setCastMembers(movieDto.getCastMembers());

        movie = movieRepository.save(movie);

        return modelMapper.map(movie, MovieDto.class);
    }

    public List<MovieDto> getMoviesByGenre(String genre) {
        List<Movie> movies = movieRepository.findByGenreContainingIgnoreCase(genre);
        return movies.stream()
                     .map(movie -> modelMapper.map(movie, MovieDto.class))
                     .collect(Collectors.toList());
    }
    

    // Get all the movies
    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();

        // Use ModelMapper to map each Movie to MovieDto
        return movies.stream()
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public MovieDto getMovieById(Long id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie != null) {
            return modelMapper.map(movie, MovieDto.class);
        }
        return null;
    }
}