package com.movies.ecinema.controller;

import com.movies.ecinema.dto.LoginDto;
import com.movies.ecinema.dto.UserDto;
import com.movies.ecinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDTO) {
        UserDto newUser = userService.createUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody LoginDto loginDto) {
        return userService.loginUser(loginDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/validate-password")
    public ResponseEntity<?> validatePassword(@RequestBody LoginDto passwordValidationDTO) {
        boolean isValid = userService.validatePassword(passwordValidationDTO);
        return ResponseEntity.ok().body("{\"valid\": " + isValid + "}");
    }


    @PutMapping("/by-email")
    public ResponseEntity<UserDto> updateUserProfile(@RequestParam String email, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUserProfileByEmail(email, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
        UserDto userDTO = userService.getUserByEmail(email);
        return ResponseEntity.ok(userDTO);
    }
}


   /*
    @PutMapping("/edit-profile")
    public ResponseEntity<UserDto> updateUserProfile(@RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUserProfile(userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    

    //@PutMapping("/{id}")
    //public ResponseEntity<UserDto> updateUser(@PathVariable long id, @RequestBody UserDto userDTO) {
      //  UserDto updatedUser = userService.updateUser(id, userDTO);
        //return ResponseEntity.ok(updatedUser);
    //}

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        UserDto userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
  */