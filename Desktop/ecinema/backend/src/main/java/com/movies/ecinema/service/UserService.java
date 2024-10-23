package com.movies.ecinema.service;

import com.movies.ecinema.dto.UserDto;
import com.movies.ecinema.entity.User.Role;
import com.movies.ecinema.dto.LoginDto;
import com.movies.ecinema.dto.PaymentCardDto;

import java.util.List;

public interface UserService {

    // Create a new user
    UserDto createUser(UserDto userDto);

    // Get all users from the repository database 
    List<UserDto> getAllUsers();

     UserDto loginUser(LoginDto loginDto);


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
