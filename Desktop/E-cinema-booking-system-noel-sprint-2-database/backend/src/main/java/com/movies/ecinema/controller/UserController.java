package com.movies.ecinema.controller;

import com.movies.ecinema.entity.User;
import com.movies.ecinema.service.UserService;
import com.movies.ecinema.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users") // Changed to "/users" as you requested
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    // Create a new user
    // @PostMapping
    // public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
    // Convert DTO to entity using ModelMapper
    // User user = modelMapper.map(userDto, User.class);
    // userService.updateUser(user); // Use update method for both create and update
    // scenarios

    // Convert entity back to DTO using ModelMapper
    // UserDto createdUserDto = modelMapper.map(user, UserDto.class);
    // return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    // }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        // Ensure the ID is not provided by the client
        userDto.setId(0); // This ensures that it's treated as a new user by the system

        // Convert DTO to entity
        User user = modelMapper.map(userDto, User.class);

        // Save new user
        userService.saveUser(user);

        // Convert entity back to DTO
        UserDto savedUserDto = modelMapper.map(user, UserDto.class);
        return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
    }

    // Get all users
    @GetMapping
    public List<UserDto> getAllUsers() {
        List<User> users = userService.getAllUsers();
        // Convert the list of User entities to a list of UserDto objects using
        // ModelMapper
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        Optional<User> userOptional = userService.getUserById(id);
        return userOptional.map(user -> new ResponseEntity<>(modelMapper.map(user, UserDto.class), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
