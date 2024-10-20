package com.movies.ecinema.dto;

import com.movies.ecinema.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private User.Status status;

    // Constructor to create UserDTO from User entity
    public UserDto(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.status = user.getStatus();
    }
}
