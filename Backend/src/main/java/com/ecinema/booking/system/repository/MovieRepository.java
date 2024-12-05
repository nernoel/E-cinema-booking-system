package ecinema.booking.system.repository;

import ecinema.booking.system.dto.MovieDto;
import ecinema.booking.system.entity.Movie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByGenreContainingIgnoreCase(String genre);
     MovieDto getMovieById(Long id); 
}