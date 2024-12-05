package ecinema.booking.system.service;

import ecinema.booking.system.dto.LoginDto;
import ecinema.booking.system.dto.UserDto;
import ecinema.booking.system.entity.PaymentCard;
import ecinema.booking.system.entity.User;
import ecinema.booking.system.repository.PaymentCardRepository;
import ecinema.booking.system.repository.UserRepository;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final PaymentCardRepository paymentCardRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, PaymentCardRepository
            paymentCardRepository, ModelMapper modelMapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;

        this.paymentCardRepository = paymentCardRepository;

        this.modelMapper = modelMapper;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User();

        user.setFirstname(userDto.getFirstname());

        user.setLastname(userDto.getLastname());

        user.setEmail(userDto.getEmail());

        user.setBillingAddress(userDto.getBillingAddress());

        // Hash the password before setting it in the entity
        String hashedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        user.setPassword(hashedPassword);

        user.setStatus(User.Status.INACTIVE);

        user.setRole(User.Role.USER);

        user.setPromoStatus(User.PromoStatus.UNSUBSCRIBED);

        user = userRepository.save(user);

        if (userDto.getPaymentCards() != null) {
            for (PaymentCard card : userDto.getPaymentCards()) {
                card.setUser(user);
                paymentCardRepository.save(card);
            }
        }

        return modelMapper.map(user, UserDto.class);
    }

    @Override
public UserDto loginUser(LoginDto loginDto) {
    // Find user by email
    User user = userRepository.findByEmail(loginDto.getEmail());

    // Check if user exists
    if (user == null) {
        throw new IllegalArgumentException("Invalid email or password");
    }

    // Check if account is inactive
    if (user.getStatus() == User.Status.INACTIVE) {
        throw new IllegalArgumentException("Account is inactive. Please contact support.");
    }

    // Verify password using BCryptPasswordEncoder
    if (!bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
        throw new IllegalArgumentException("Invalid email or password");
    }

    user.setStatus(User.Status.ACTIVE); 
    userRepository.save(user);

    // Map the user entity to a DTO
    return modelMapper.map(user, UserDto.class);
    // userDto.setRole(user.getRole()); // Add user role if required
    // return userDto;
}


    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Use ModelMapper to map each User to UserDto
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Long getUserIdByEmail(String email) {
       User user = userRepository.findByEmail(email);
       Long userId = user.getId();
       return userId;
    }

    @Override
    public UserDto updatePromoStatusById(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPromoStatus(userDto.getPromoStatus()); 

        User updatedUser = userRepository.save(user);

        return modelMapper.map(updatedUser, UserDto.class);
    }   

    public boolean updateUserStatusToActive(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setStatus(User.Status.ACTIVE);
            userRepository.save(user);    
            return true;
        }
        return false;
    }
}