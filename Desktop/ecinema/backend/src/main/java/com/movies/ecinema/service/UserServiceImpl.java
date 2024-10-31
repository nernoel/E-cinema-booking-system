package com.movies.ecinema.service;

import com.movies.ecinema.dto.LoginDto;
import com.movies.ecinema.dto.UserDto;
import com.movies.ecinema.entity.PaymentCard;
import com.movies.ecinema.entity.User;
import com.movies.ecinema.entity.User.Role;
import com.movies.ecinema.entity.User.Status;
import com.movies.ecinema.repository.PaymentCardRepository;
import com.movies.ecinema.repository.UserRepository;

import java.util.List;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PaymentCardRepository paymentCardRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PaymentCardRepository paymentCardRepository) {
        this.userRepository = userRepository;
        this.paymentCardRepository = paymentCardRepository;
    }

    public boolean validatePassword(LoginDto passwordValidationDTO) {
        User user = userRepository.findByEmail(passwordValidationDTO.getEmail());
        if (user != null) {
            // Directly compare the raw password
            return passwordValidationDTO.getPassword().equals(user.getPassword());
        }
        return false; // User not found
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User();

        user.setFirstname(userDto.getFirstname());

        user.setLastname(userDto.getLastname());

        user.setEmail(userDto.getEmail());

        user.setBillingAddress(userDto.getBillingAddress());

        user.setPassword(userDto.getPassword());

        user.setStatus(Status.ACTIVE);

        user.setRole((Role.USER));

        // Save the user first to get the ID
        user = userRepository.save(user);

        // Set user reference on each payment card and save
        if (userDto.getPaymentCards() != null) {
            for (PaymentCard card : userDto.getPaymentCards()) {
                card.setUser(user);
                paymentCardRepository.save(card);
            }
        }

        // Send confirmation email for registration
        String subject = "Registration Confirmation";
        String body = "Dear " + userDto.getFirstname()
                + ",\n\nYou have successfully created a new account.\n\nBest regards,\nYour ECinema Team";
        emailService.sendConfirmationEmail(userDto.getEmail(), subject, body);

        return mapToDto(user);
    }

    /*
     * Retrieve all users
     */
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        // Map each User entity to UserDto
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto loginUser(LoginDto loginDto) {
        // Find user by email
        User user = userRepository.findByEmail(loginDto.getEmail());

        // Check if user exists and compare plain-text passwords
        if (user == null || !user.getPassword().equals(loginDto.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Map and return the user DTO if credentials are valid
        return mapToDto(user);
    }

    @Override
    public UserDto updateUserProfileByEmail(String email, UserDto userDto) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        user.setFirstname(userDto.getFirstname());

        user.setLastname(userDto.getLastname());

        user.setBillingAddress(userDto.getBillingAddress());

        // Update or add payment cards
    if (userDto.getPaymentCards() != null) {
        // Clear existing cards to prevent conflicts
        if (user.getPaymentCards() != null) {
            user.getPaymentCards().clear();
        } else {
            user.setPaymentCards(new ArrayList<PaymentCard>());
        }

        // Add each card from the DTO to the user's list
        for (PaymentCard cardDto : userDto.getPaymentCards()) {
            PaymentCard newCard = new PaymentCard();
            
            newCard.setCardName(cardDto.getCardName());

            newCard.setNumber(cardDto.getNumber());

            newCard.setSecurityCode(cardDto.getSecurityCode());

            newCard.setExpiryDate(cardDto.getExpiryDate());

            newCard.setCardType(cardDto.getCardType());

            newCard.setUser(user); // Set the user reference on the card

            user.getPaymentCards().add(newCard); // Add to user's list
        }
    }

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(userDto.getPassword());
        }

        // Send confirmation email for registration
        //String subject = "Profile updat information";
        //String body = "Dear " + userDto.getFirstname()
          //      + ",\n\nYou have successfully updated your account.\n\nBest regards,\nYour ECinema Team";
        //emilService.sendConfirmationEmail(userDto.getEmail(), subject, body);

        
        // Save the updated user
        User updatedUser = userRepository.save(user);

        // Return the updated user DTO
        return mapToDto(updatedUser);
        
        }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        return mapToDto(user);
    }

    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());

        userDto.setFirstname(user.getFirstname());

        userDto.setLastname(user.getLastname());

        userDto.setEmail(user.getEmail());

        userDto.setBillingAddress(user.getBillingAddress());

        userDto.setStatus(user.getStatus());

        userDto.setRole(user.getRole());

        userDto.setPaymentCards(user.getPaymentCards());

        return userDto;
    }
}

/*
 * @Override
 * public UserDto loginUser(LoginDto loginDto) {
 * // Find user by email
 * User user = userRepository.findByEmail(loginDto.getEmail());
 * 
 * // Check if user exists and compare plain-text passwords
 * if (user == null || !user.getPassword().equals(loginDto.getPassword())) {
 * throw new IllegalArgumentException("Invalid email or password");
 * }
 * 
 * // Map and return the user DTO if credentials are valid
 * return mapToDto(user);
 * }
 * 
 * @Override
 * public List<UserDto> getAllUsers() {
 * // Find all users from user repo
 * List<User> users = userRepository.findAll();
 * 
 * // Map users to Dto and place into list
 * return users.stream()
 * .map(this::mapToDto)
 * .collect(Collectors.toList());
 * }
 * 
 * @Override
 * public UserDto updateUserProfile(UserDto userDto) {
 * // Fetch user from the database using the email
 * User user = userRepository.findByEmail(userDto.getEmail());
 * 
 * if (user == null) {
 * throw new IllegalArgumentException("User not found");
 * }
 * 
 * // Update fields except for the email
 * user.setFirstname(userDto.getFirstname());
 * user.setLastname(userDto.getLastname());
 * user.setBillingAddress(userDto.getBillingAddress());
 * 
 * // Only update the password if a new password is provided
 * if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
 * user.setPassword((userDto.getPassword()));
 * }
 * 
 * // Save updated user information
 * user = userRepository.save(user);
 * 
 * return mapToDto(user);
 * }
 * 
 * @Override
 * public UserDto updateUser(long id, UserDto userDto) {
 * User user = userRepository.findById(id)
 * .orElseThrow(() -> new RuntimeException("User not found"));
 * user.setFirstname(userDto.getFirstname());
 * user.setLastname(userDto.getLastname());
 * user.setEmail(userDto.getEmail());
 * user.setBillingAddress(userDto.getBillingAddress());
 * user.setStatus(userDto.getStatus());
 * 
 * User updatedUser = userRepository.save(user);
 * return mapToDto(updatedUser);
 * }
 * 
 * @Override
 * public void deleteUser(long id) {
 * userRepository.deleteById(id);
 * }
 * 
 * @Override
 * public UserDto getUserById(long id) {
 * User user = userRepository.findById(id)
 * .orElseThrow(() -> new RuntimeException("User not found"));
 * return mapToDto(user);
 * }
 * 
 * @Override
 * public UserDto getUserByEmail(String email) {
 * User user = userRepository.findByEmail(email);
 * 
 * return mapToDto(user);
 * }
 * 
 * 
 * @Override
 * public UserDto updateUserProfileByEmail(String email, UserDto userDto) {
 * User user = userRepository.findByEmail(email);
 * if (user == null) {
 * throw new RuntimeException("User not found");
 * }
 * 
 * // Update user's basic information
 * user.setFirstname(userDto.getFirstname());
 * user.setLastname(userDto.getLastname());
 * user.setBillingAddress(userDto.getBillingAddress());
 * 
 * if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
 * user.setPassword(userDto.getPassword());
 * }
 * 
 * User updatedUser = userRepository.save(user);
 * return mapToDto(updatedUser);
 * }
 * 
 * private UserDto mapToDto(User user) {
 * UserDto userDto = new UserDto();
 * 
 * // Set all user information
 * userDto.setId(user.getId());
 * userDto.setFirstname(user.getFirstname());
 * userDto.setLastname(user.getLastname());
 * userDto.setEmail(user.getEmail());
 * userDto.setBillingAddress(user.getBillingAddress());
 * userDto.setStatus(user.getStatus());
 * userDto.setRole(user.getRole());
 * 
 * 
 * return userDto;
 * }
 * }
 * 
 * private UserDto mapToDto(User user) {
 * UserDto userDto = new UserDto();
 * userDto.setId(user.getId());
 * userDto.setFirstname(user.getFirstname());
 * userDto.setLastname(user.getLastname());
 * userDto.setEmail(user.getEmail());
 * userDto.setBillingAddress(user.getBillingAddress());
 * userDto.setPassword(user.getPassword());
 * userDto.setStatus(user.getStatus());
 * userDto.setRole(user.getRole());
 * userDto.setPaymentCards(user.getPaymentCards());
 * return userDto;
 * }
 */