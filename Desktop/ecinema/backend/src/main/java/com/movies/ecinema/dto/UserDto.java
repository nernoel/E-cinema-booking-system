package com.movies.ecinema.dto;

import java.util.List;

import com.movies.ecinema.entity.User.Role;

import lombok.*;

@Data
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
    private String password; 
    private String status;
    private String role;  

    private List<PaymentCardDto> paymentCards;

    public String getRole() {
        return role;
    }

public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}
}
