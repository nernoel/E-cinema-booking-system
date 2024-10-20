/*
 * Movie DTO to transfer movie information without exposing
 * to the client sensitive information
 */

package com.movies.ecinema.dto;

import lombok.*;

@Setter // Create setter methods
@Getter // Create getter methods
@NoArgsConstructor // Create default constructor
@AllArgsConstructor // Create all args constructor

@Data
public class MovieDto {
    private long id;

    private String title;

    private String genre;

    private int runtime;

    private String trailer;

    private String description;

    private String releaseDate;

    private String moviePosterLink;

    private String category;
}
