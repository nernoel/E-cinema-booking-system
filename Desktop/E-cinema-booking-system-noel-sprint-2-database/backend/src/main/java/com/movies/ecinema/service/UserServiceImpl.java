package com.movies.ecinema.service;

import com.movies.ecinema.entity.PaymentCard;
import com.movies.ecinema.entity.User;
import com.movies.ecinema.repository.UserRepository;
import com.movies.ecinema.repository.PaymentCardRepository;
import com.movies.ecinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PaymentCardRepository paymentCardRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PaymentCardRepository paymentCardRepository) {
        this.userRepository = userRepository;
        this.paymentCardRepository = paymentCardRepository;
    }

    @Override
    public void addPaymentCardToUser(long userId, PaymentCard paymentCard) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPaymentCards().size() >= 4) {
                throw new IllegalStateException("A user cannot have more than 4 payment cards.");
            }
            paymentCard.setUser(user);
            paymentCardRepository.save(paymentCard);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

    @Override
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User not found with ID: " + user.getId());
        }
        userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public void saveUser(User user) {
        // The userRepository save method will insert a new user if the ID is null or 0
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
