package com.movies.ecinema.service;

import com.movies.ecinema.dto.UserDto;
import com.movies.ecinema.dto.PaymentCardDto;
import com.movies.ecinema.entity.PaymentCard;
import com.movies.ecinema.entity.User;
import com.movies.ecinema.entity.User.Role;
import com.movies.ecinema.repository.PaymentCardRepository;
import com.movies.ecinema.repository.UserRepository;
import com.movies.ecinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentCardRepository paymentCardRepository;

    @Override
public UserDto createUser(UserDto userDto) {
    User user = new User();
    user.setFirstname(userDto.getFirstname());
    user.setLastname(userDto.getLastname());
    user.setEmail(userDto.getEmail());
    user.setAddress(userDto.getAddress());
    user.setPassword(userDto.getPassword());
    user.setStatus(userDto.getStatus());
    
    // Check and set role
    if (userDto.getRole() != null) {
        user.setRole(userDto.getRole());
    } else {
        user.setRole("USER"); // Default role if none provided
    }

    // Save the user to the database
    user = userRepository.save(user);
    return mapToDto(user);
}




    @Override
    public UserDto updateUser(long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setStatus(userDto.getStatus());

        User updatedUser = userRepository.save(user);
        return mapToDto(updatedUser);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // Payment card management
    @Override
    public PaymentCardDto addPaymentCard(long userId, PaymentCardDto paymentCardDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PaymentCard paymentCard = mapToPaymentCardEntity(paymentCardDto);
        paymentCard.setUser(user);
        PaymentCard savedCard = paymentCardRepository.save(paymentCard);

        return mapToPaymentCardDto(savedCard);
    }

    @Override
    public List<PaymentCardDto> getPaymentCardsForUser(long userId) {
        Optional<PaymentCard> paymentCards = paymentCardRepository.findById(userId);
        return paymentCards.stream().map(this::mapToPaymentCardDto).collect(Collectors.toList());
    }

    @Override
    public void deletePaymentCard(long userId, long cardId) {
        paymentCardRepository.deleteById(cardId);
    }

    // Helper methods
    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setAddress(user.getAddress());
       //user.setStatus("ACTIVE");  // Set the user's status to INACTIVE
        userDto.setStatus(user.getStatus());  // Set the userDto's status as a string
        


        // Add a null check for the user's status
        if (user.getStatus() != null) {
            userDto.setStatus(user.getStatus());
        } else {
            userDto.setStatus("INACTIVE"); // Or any default status you'd prefer
        }

        // Convert user's payment cards to dtos
        List<PaymentCardDto> paymentCards = user.getPaymentCards().stream()
                .map(this::mapToPaymentCardDto).collect(Collectors.toList());
        userDto.setPaymentCards(paymentCards);

        return userDto;
    }

    @Override
    public List<UserDto> getUserByRole(String role) {
    List<User> users = userRepository.findByRole(role); // Fetch users by role

    // Map the User entities to UserDto and collect into a list
    return users.stream()
                .map(this::mapToDto) // Assuming you have a mapToDto method
                .collect(Collectors.toList());
}

    /* 
    private User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        return user;
    }
        */

    private PaymentCardDto mapToPaymentCardDto(PaymentCard paymentCard) {
        PaymentCardDto paymentCardDto = new PaymentCardDto();
        paymentCardDto.setId(paymentCard.getId());
        paymentCardDto.setCardNumber(paymentCard.getCardNumber());
        paymentCardDto.setCardHolderName(paymentCard.getCardHolderName());
        paymentCardDto.setExpiryDate(paymentCard.getExpiryDate());
        paymentCardDto.setCardType(paymentCard.getCardType());
        paymentCardDto.setId(paymentCard.getUser().getId());
        return paymentCardDto;
    }

    private PaymentCard mapToPaymentCardEntity(PaymentCardDto paymentCardDto) {
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardNumber(paymentCardDto.getCardNumber());
        paymentCard.setCardHolderName(paymentCardDto.getCardHolderName());
        paymentCard.setExpiryDate(paymentCardDto.getExpiryDate());
        paymentCard.setCardType(paymentCardDto.getCardType());
        return paymentCard;
    }

    
}
