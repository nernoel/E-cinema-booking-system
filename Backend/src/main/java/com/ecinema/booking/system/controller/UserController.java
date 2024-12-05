package ecinema.booking.system.controller;

import ecinema.booking.system.dto.LoginDto;
import ecinema.booking.system.dto.UserDto;
import ecinema.booking.system.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDTO) {
        UserDto newUser = userService.createUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
      //  try {
            UserDto userDto = userService.loginUser(loginDto);
            return ResponseEntity.ok(userDto);
        //} catch (IllegalArgumentException ex) {
          //  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        //} catch (Exception ex) {
           // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login.");
       // }
        
    }
    

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get-id/by-email")
    public ResponseEntity<Long> getUserIdByEmail(@RequestParam String email) {
        Long userId = userService.getUserIdByEmail(email);
        return ResponseEntity.ok(userId);
    }

    @PutMapping("{userId}/promo-status")
    public ResponseEntity<UserDto> updatePromoStatus(
        @PathVariable Long userId, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updatePromoStatusById(userId, userDto);
    return ResponseEntity.ok(updatedUser);
    }


}