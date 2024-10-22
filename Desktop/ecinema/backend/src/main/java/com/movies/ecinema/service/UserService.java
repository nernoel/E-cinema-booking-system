package com.movies.ecinema.service;

import com.movies.ecinema.dto.UserDto;
import com.movies.ecinema.entity.User.Role;
import com.movies.ecinema.dto.PaymentCardDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(long id, UserDto userDto);
    void deleteUser(long id);
    UserDto getUserById(long id);
    UserDto getUserByEmail(String email);
    public List<UserDto> getUserByRole(String role); 
    List<UserDto> getAllUsers();

    // Payment card management
    PaymentCardDto addPaymentCard(long userId, PaymentCardDto paymentCardDto);
    List<PaymentCardDto> getPaymentCardsForUser(long userId);
    void deletePaymentCard(long userId, long cardId);
}
