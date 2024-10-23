package com.movies.ecinema.dto;

import java.util.List;

import com.movies.ecinema.entity.User.Role;
import com.movies.ecinema.entity.User.Status;

public class UserDto {

    private long id;

    private String firstname;

    private String lastname;

    private String email;

    private String billing_address;

    private String password;

    private Status status;

    private Role role;

    private List<PaymentCardDto> paymentCards;

    // No-args constructor
    public UserDto() {
    }

    // All-args constructor
    public UserDto(long id, String firstname, String lastname, String email, String billing_address, String password, Status status, Role role, List<PaymentCardDto> paymentCards) {
        this.id = id;

        this.firstname = firstname;

        this.lastname = lastname;

        this.email = email;

        this.billing_address = billing_address;

        this.password = password;

        this.status = status;
        
        this.role = role;

        this.paymentCards = paymentCards;
    }

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

    public String getBillingAddress() {
        return billing_address;
    }

    public void setBillingAddress(String billing_address) {
        this.billing_address = billing_address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<PaymentCardDto> getPaymentCards() {
        return paymentCards;
    }

    public void setPaymentCards(List<PaymentCardDto> paymentCards) {
        this.paymentCards = paymentCards;
    }
}
