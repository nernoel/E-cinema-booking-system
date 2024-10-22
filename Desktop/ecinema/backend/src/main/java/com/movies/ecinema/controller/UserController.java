package com.movies.ecinema.controller;

import com.movies.ecinema.dto.UserDto;
import com.movies.ecinema.dto.UserDto;
import com.movies.ecinema.dto.PaymentCardDto;
import com.movies.ecinema.dto.PaymentCardDto;
import com.movies.ecinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDTO) {
        UserDto newUser = userService.createUser(userDTO);
        
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable long id, @RequestBody UserDto userDTO) {
        UserDto updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        UserDto userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/email")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
        UserDto userDTO = userService.getUserByEmail(email);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Payment card endpoints
    @PostMapping("/{userId}/payment-cards")
    public ResponseEntity<PaymentCardDto> addPaymentCard(@PathVariable long userId, @RequestBody PaymentCardDto paymentCardDTO) {
        PaymentCardDto newCard = userService.addPaymentCard(userId, paymentCardDTO);
        return new ResponseEntity<>(newCard, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/payment-cards")
    public ResponseEntity<List<PaymentCardDto>> getPaymentCardsForUser(@PathVariable long userId) {
        List<PaymentCardDto> paymentCards = userService.getPaymentCardsForUser(userId);
        return ResponseEntity.ok(paymentCards);
    }

    @DeleteMapping("/{userId}/payment-cards/{cardId}")
    public ResponseEntity<Void> deletePaymentCard(@PathVariable long userId, @PathVariable long cardId) {
        userService.deletePaymentCard(userId, cardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
