package com.movies.ecinema.service;

import com.movies.ecinema.entity.User;
import com.movies.ecinema.entity.PaymentCard;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void addPaymentCardToUser(long userId, PaymentCard paymentCard);

    Optional<User> getUserById(long id);

    void updateUser(User user);

    void deleteUser(long id);

    void saveUser(User user);

    // New method to get all users
    List<User> getAllUsers();
}
