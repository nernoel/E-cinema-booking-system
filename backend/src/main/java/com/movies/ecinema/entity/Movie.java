package com.movies.ecinema.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter // Create setter methods
@Getter // Create getter methods
@NoArgsConstructor // create default constructor
@AllArgsConstructor // create all args constructor

// Mapping to database table
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Movie title

    private String title;

    // Movie genre
    // @Column(nullable = false)
    private String genre;

    // Movie runtime

    private int runtime;

    // Movie description

    private String description;

    // Movie image link

    private String moviePosterLink;

    // Movie release date

    private String releaseDate;

    private String category;

    private String trailer;
}
