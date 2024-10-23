package com.movies.ecinema.service;

import com.movies.ecinema.dto.UserDto;
import com.movies.ecinema.dto.PaymentCardDto;
import com.movies.ecinema.entity.PaymentCard;
import com.movies.ecinema.entity.User;
// import com.movies.ecinema.repository.PaymentCardRepository;
import com.movies.ecinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
// import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    /*
     * Importing users from JPA repo
     */
    @Autowired
    private UserRepository userRepository;

     /*
     * Importing payment card information from JPA repo
     */
  
    // private PaymentCardRepository paymentCardRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        // Call new user constructor
        User user = new User();

        /*
         * Setting user information from userDto object
         */ 
        user.setFirstname(userDto.getFirstname());

        user.setLastname(userDto.getLastname());

        user.setEmail(userDto.getEmail());

        user.setBillingAddress(userDto.getBillingAddress());

        user.setPassword(userDto.getPassword());

        user.setStatus(userDto.getStatus());

        user.setRole(userDto.getRole());

        // Save user to JPA repo
        user = userRepository.save(user);

        // Map the DTO to the user object
        return mapToDto(user);
    }

    /*
     * Get all users from the database
     */
    @Override
    public List<UserDto> getAllUsers() {

        // Find all users from user repo
        List<User> users = userRepository.findAll();

        // Map users to Dto and place into list
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    /*
     * Mapping user dto to user object
     */
    private UserDto mapToDto(User user) {
        // Create userDto constructor
        UserDto userDto = new UserDto();

        /* 
         * Set all user information
         */
        userDto.setId(user.getId());

        userDto.setFirstname(user.getFirstname());

        userDto.setLastname(user.getLastname());

        userDto.setPassword(user.getPassword());

        userDto.setEmail(user.getEmail());

        userDto.setBillingAddress(user.getBillingAddress());

        userDto.setStatus(user.getStatus());

        userDto.setRole(user.getRole());

        /*
         * Map each payment card via stream to collect users to return list of user
         * payment cards for each user
         */
        List<PaymentCardDto> paymentCards = user.getPaymentCards().stream()
                .map(this::mapToPaymentCardDto).collect(Collectors.toList());
        userDto.setPaymentCards(paymentCards);

        // Return the data access object
        return userDto;
    }

    /*
     * Map each payment card to the payment card data object
     */
    private PaymentCardDto mapToPaymentCardDto(PaymentCard paymentCard) {
        // Construct payment card dto object
        PaymentCardDto paymentCardDto = new PaymentCardDto();
        /*
         * Set payment card information
         */
        paymentCardDto.setId(paymentCard.getId());

        paymentCardDto.setCardNumber(paymentCard.getCardNumber());

        paymentCardDto.setCardHolderName(paymentCard.getCardHolderName());

        paymentCardDto.setExpiryDate(paymentCard.getExpiryDate());

        paymentCardDto.setCardType(paymentCard.getCardType());

        // Return payment card data access object
        return paymentCardDto;
    }

    

    /* UNUSED CODE FOR NOW! SAVE INCASE NEEDED LATER
    @Override
    public UserDto updateUser(long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setBillingAddress(userDto.getBilling_address());
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
        List<PaymentCard> paymentCards = paymentCardRepository.findByUserId(userId);
        return paymentCards.stream().map(this::mapToPaymentCardDto).collect(Collectors.toList());
    }
        @Override
    public void deletePaymentCard(long userId, long cardId) {
        PaymentCard paymentCard = paymentCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Payment card not found"));
        if (paymentCard.getUser().getId() != userId) {
            throw new RuntimeException("Payment card does not belong to user");
        }
        paymentCardRepository.deleteById(cardId);
    }

    private PaymentCard mapToPaymentCardEntity(PaymentCardDto paymentCardDto) {
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardNumber(paymentCardDto.getCardNumber());
        paymentCard.setCardHolderName(paymentCardDto.getCardHolderName());
        paymentCard.setExpiryDate(paymentCardDto.getExpiryDate());
        paymentCard.setCardType(paymentCardDto.getCardType());
        return paymentCard;
    }

     */
}
