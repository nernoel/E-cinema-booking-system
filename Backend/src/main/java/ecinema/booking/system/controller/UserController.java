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
        UserDto userDto = userService.loginUser(loginDto);
        return ResponseEntity.ok(userDto);
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

    // New method for editing user profile
    @PutMapping("/{userId}/edit-profile")
    public ResponseEntity<UserDto> editProfile(
        @PathVariable Long userId, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.editProfile(userId, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    // Fetch user information by email
    @GetMapping("/by-email") 
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
        UserDto userDto = userService.getUserByEmail(email);
        if (userDto != null) {
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/edit-profile/by-email")
    public ResponseEntity<UserDto> updateProfileByEmail(@RequestParam String email, @RequestBody UserDto userDto) {
    UserDto updatedUser = userService.updateProfileByEmail(email, userDto);
    return ResponseEntity.ok(updatedUser);
}


}
