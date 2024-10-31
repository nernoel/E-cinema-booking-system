package com.movies.ecinema.dto;

import java.util.List;

import com.movies.ecinema.entity.PaymentCard;
import com.movies.ecinema.entity.User.Role;
import com.movies.ecinema.entity.User.Status;

import lombok.*;

@Setter 

@NoArgsConstructor 
@AllArgsConstructor 

public class UserDto {

    private long id;

    private String firstname;

    private String lastname;

    private String email;

    private String billingAddress;

    private String password;

    private Status status;

    private Role role;

    private List<PaymentCard> paymentCards;

    // Getter for id
    public long getId() {
        return id;
    }

    // Getter for firstname
    public String getFirstname() {
        return firstname;
    }

    // Getter for lastname
    public String getLastname() {
        return lastname;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Getter for billingAddress
    public String getBillingAddress() {
        return billingAddress;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Getter for status
    public Status getStatus() {
        return status;
    }

    // Getter for role
    public Role getRole() {
        return role;
    }

    // Getter for paymentCards
    public List<PaymentCard> getPaymentCards() {
        return paymentCards;
    }
}