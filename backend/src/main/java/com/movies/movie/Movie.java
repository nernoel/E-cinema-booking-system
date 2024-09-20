package com.movies.movie;

import jakarta.persistence.*;
import lombok.*;

@Setter // Create setter methods
@Getter // Create getter methods
@NoArgsConstructor  // create default constructor
@AllArgsConstructor // create all args constructor

// Mapping to database table
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Movie title
    @Column(nullable = false)
    private String title;

    // Movie description
    @Column(nullable = false)
    private String description;

    // Movie released boolean
    private boolean isReleased;

}
