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

    /*
     * Register a new user
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDTO) {
        UserDto newUser = userService.createUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /*
     * Login an existing user
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        UserDto userDto = userService.loginUser(loginDto);
        return ResponseEntity.ok(userDto);
    }

    /*
     * Fetch all existing users
     */
    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
 * Fetch userId by email
 */
@GetMapping("/get-id/{email}")
public ResponseEntity<Long> getUserIdByEmail(@PathVariable String email) {
    Long userId = userService.getUserIdByEmail(email);
    return ResponseEntity.ok(userId);
}


    /*
     * Fetch user by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@RequestParam Long id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    /*
     * Edit user promotional status using userId
     */
    @PutMapping("{userId}/promo-status")
    public ResponseEntity<UserDto> updatePromoStatus(
            @PathVariable Long userId, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updatePromoStatusById(userId, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    /*
     * Edit user information using userId
     */
    @PutMapping("/{userId}/edit-profile")
    public ResponseEntity<UserDto> editProfile(
            @PathVariable Long userId, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.editProfile(userId, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    /**
 * Fetch user by email
 */
@GetMapping("/get-user")
public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
    UserDto userDto = userService.getUserByEmail(email);
    if (userDto != null) {
        return ResponseEntity.ok(userDto);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}



    /*
     * @PutMapping("/edit-profile/by-email")
     * public ResponseEntity<UserDto> updateProfileByEmail(@RequestParam String
     * email, @RequestBody UserDto userDto) {
     * UserDto updatedUser = userService.updateProfileByEmail(email, userDto);
     * return ResponseEntity.ok(updatedUser);
     * 
     * // Method 1: Accepts a String for email
     * 
     * @PutMapping("{userEmail}/promo-status")
     * public ResponseEntity<UserDto> updatePromoStatus(
     * 
     * @PathVariable String userEmail, @RequestBody UserDto userDto) {
     * User.PromoStatus promoStatus = userDto.getPromoStatus();
     * // Validate and process the promoStatus as needed
     * UserDto updatedUser = userService.updatePromoStatusByEmail(userEmail,
     * promoStatus);
     * return ResponseEntity.ok(updatedUser);
     * }
     * }
     */
}
