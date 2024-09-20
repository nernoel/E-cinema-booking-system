/*
 * Movie DTO to transfer movie information without exposing
 * to the client sensitive information
 */

package com.movies.dto;

import lombok.*;

@Setter // Create setter methods
@Getter // Create getter methods
@NoArgsConstructor // Create default constructor
@AllArgsConstructor // Create all args constructor


public class MovieDto {
    private String title;
    private String genre;
    private int runtime;
    private String description;
    private String releaseDate;
}
