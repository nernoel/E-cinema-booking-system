package ecinema.booking.system.service;

import ecinema.booking.system.dto.LoginDto;
import ecinema.booking.system.dto.UserDto;

import java.util.List;

public interface UserService {
    // Create new user
    UserDto createUser(UserDto userDto);

    // Login user
    UserDto loginUser(LoginDto loginDto);

    // Fetch all users
    List<UserDto> getAllUsers();

    // Get a user via email address
    Long getUserIdByEmail(String email);

    public UserDto getUserByEmail(String email);

    // Update user promo status
    UserDto updatePromoStatusById(Long userId, UserDto userDto);

    // Update user active status in the database
    boolean updateUserStatusToActive(String email);

    public UserDto editProfile(Long userId, UserDto userDto);

    public UserDto updateProfileByEmail(String email, UserDto userDto);
}