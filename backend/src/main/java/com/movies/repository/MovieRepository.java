/*
 * Movie repositoryt to interact with the database
 * Extends JPA imported repository
 */
package com.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.movies.entity.Movie;

@Repository
// Finds movie by Id long and of Movie type
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Custom query implementations:
    
}
