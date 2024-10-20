package com.movies.ecinema.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstname;
    private String lastname;
    private String email;
    private String address;  // Correct spelling from 'adress' to 'address'

    @Enumerated(EnumType.STRING)
    private Status status;


    // One-to-many relationship with PaymentCard
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentCard> paymentCards;

    public enum Status {
        ACTIVE,
        INACTIVE
    }
    // Manually defining the all-args constructor
    public User(long id, String firstname, String lastname, String email, String address, Status status, List<PaymentCard> paymentCards) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.status = status;
        this.paymentCards = paymentCards;
    }
}
