package com.movies.ecinema.service;

import com.movies.ecinema.dto.UserDto;
import com.movies.ecinema.entity.PaymentCard;
import com.movies.ecinema.dto.LoginDto;
import com.movies.ecinema.dto.PaymentCardDto;

import java.util.List;

public interface UserService {
    // Create a new user
    UserDto createUser(UserDto userDto);

    // Get all users from the repository database
    List<UserDto> getAllUsers();

    // User login
    UserDto loginUser(LoginDto loginDto);

    // Update user profile
    UserDto updateUserProfile(UserDto userDto);

    // Get user info via ID
    UserDto getUserById(long id);

    // Get user info via email
    UserDto getUserByEmail(String email);


    // Delete user info via ID
    void deleteUser(long id);

    // Profile update based on email
    UserDto updateUserProfileByEmail(String email, UserDto userDto);
}
