package com.movies.ecinema.service;

import com.movies.ecinema.dto.LoginDto;
import com.movies.ecinema.dto.UserDto;

import java.util.List;

public interface UserService {
    // Create a new user
    UserDto createUser(UserDto userDto);

    // Get all users from the repository database
    List<UserDto> getAllUsers();

     // User login
    UserDto loginUser(LoginDto loginDto);

    // Get user info via email
    UserDto getUserByEmail(String email);

    // Profile update based on email
    UserDto updateUserProfileByEmail(String email, UserDto userDto);

    public boolean validatePassword(LoginDto passwordValidationDTO);
   
}
    /*
    // User login
    UserDto loginUser(LoginDto loginDto);

    // Update user profile
    UserDto updateUserProfile(UserDto userDto);

    // Get user info via ID
    UserDto getUserById(long id);

    


    // Delete user info via ID
    void deleteUser(long id);

    

    // UserDto updateUser(long id, UserDto userDto);

    // UserDto updateUser(long id, UserDto userDto);

    //PaymentCardDto addPaymentCard(long userId, PaymentCardDto paymentCardDto);

    //List<PaymentCardDto> getPaymentCardsForUser(long userId);

    // New methods for direct access to payment cards
    //PaymentCardDto getPaymentCardById(long cardId);
    
    //PaymentCardDto updatePaymentCard(long cardId, PaymentCardDto paymentCardDto);
    
    //void deletePaymentCard(long cardId);
}

*/
