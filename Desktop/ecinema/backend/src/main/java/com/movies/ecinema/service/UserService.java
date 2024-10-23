package com.movies.ecinema.service;

import com.movies.ecinema.dto.UserDto;


import java.util.List;

public interface UserService {

    // Create a new user
    UserDto createUser(UserDto userDto);

    // Get all users
    List<UserDto> getAllUsers();

    /* 

    // Getting a user info via id
    UserDto getUserById(long id);

    // Getting a user info via email
    UserDto getUserByEmail(String email);

    // Updating a current user info
    UserDto updateUser(long id, UserDto userDto);

    // Deleting a current user info
    void deleteUser(long id);
     

    /*
     * Managing payment cards for each user
     
    
    // Create a new payment card for user
    PaymentCardDto addPaymentCard(long userId, PaymentCardDto paymentCardDto);

    // Get all user payment cards
    List<PaymentCardDto> getPaymentCardsForUser(long userId);

    // Delete user payment cards
    void deletePaymentCard(long userId, long cardId);
    

    */
}
