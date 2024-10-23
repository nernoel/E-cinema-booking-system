package com.movies.ecinema.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Class representing a user object in JPA database
 * User class containing:
 * firstname
 * lastname
 * email
 * password
 * status
 */
@Entity
@Table(name = "users")
public class User {

    /* Autogenerate user ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private String billing_address;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    /*
     * Enum representing user account status
     */
    public enum Status {
        ACTIVE,
        INACTIVE
    }

    /*
     * Enum representing user account role
     */
    public enum Role {
        USER,
        ADMIN
    }

    /*
     * No args constructor(Default)
     */
    public User() {
    }

    /*
     * All args constructor
     */
    public User(long id, String firstname, String lastname, String email, String password, String billing_address, Status status, Role role, List<PaymentCard> paymentCards) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.billing_address = billing_address;
        this.status = status;
        this.role = role;
        this.paymentCards = paymentCards;
    }

    /*
     * Create a One-to-many relationship with PaymentCard
     * One user is able to have many payment cards (up to 4 stored on their account)
     * Store the payment cards into an arrayList
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentCard> paymentCards = new ArrayList<>();

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBillingAddress() {
        return billing_address;
    }

    public void setBillingAddress(String billing_address) {
        this.billing_address = billing_address;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<PaymentCard> getPaymentCards() {
        return paymentCards;
    }

    public void setPaymentCards(List<PaymentCard> paymentCards) {
        this.paymentCards = paymentCards;
    }
}
