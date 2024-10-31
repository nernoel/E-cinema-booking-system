package com.movies.ecinema.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter 
@Getter
@NoArgsConstructor 
@AllArgsConstructor 

// Mapping to database table
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private String title;

    private String genre;

    private int runtime;

    private String description;

    private String moviePosterLink;

    private String releaseDate;

    private String category;

    private String trailer;
}
