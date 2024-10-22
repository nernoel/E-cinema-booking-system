package com.movies.ecinema.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    private String address;
    private String password;
    private String status;
    private String role;

    // One-to-many relationship with PaymentCard
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentCard> paymentCards = new ArrayList<>();

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    public enum Role {
        USER,
        ADMIN
    }

    public User(long id, String firstname, String lastname, String email, String password, String address,
            String status, String role, List<PaymentCard> paymentCards) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.status = status;
        this.role = role; // Set the role
        this.paymentCards = paymentCards;
    }

}
