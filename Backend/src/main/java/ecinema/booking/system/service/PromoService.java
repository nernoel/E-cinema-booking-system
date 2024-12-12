package ecinema.booking.system.service;

import ecinema.booking.system.dto.PromoCodeDto;
import ecinema.booking.system.dto.PromoUsageDto;
import ecinema.booking.system.entity.PromoCode;
import ecinema.booking.system.entity.PromoUsage;
import ecinema.booking.system.entity.User;
import ecinema.booking.system.repository.PromoCodeRepository;
import ecinema.booking.system.repository.PromoUsageRepository;
import ecinema.booking.system.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PromoService {

    @Autowired
    private PromoCodeRepository promoCodeRepository;

    @Autowired
    private PromoUsageRepository promoUsageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    

    public PromoCodeDto applyPromoCode(Long userId, String promoCode) throws Exception {
        // Find the active promo code and user
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    
        PromoCode code = promoCodeRepository.findByCodeAndIsActive(promoCode, PromoCode.IsActive.ACTIVE)
                .orElseThrow(() -> new Exception("Promo code is invalid or inactive"));
    
        // Check if the user has already used the promo code
        Optional<PromoUsage> usageOpt = promoUsageRepository.findByUserIdAndPromoCodeId(userId, code.getId());
        if (usageOpt.isPresent() && usageOpt.get().getUsageStatus() == PromoUsage.UsageStatus.USED) {
            throw new Exception("Promo code already used by the user");
        }
    
        // If not used, create or update the usage record
        PromoUsage usage = usageOpt.orElse(new PromoUsage());
        usage.setUser(new User(userId)); // assuming User constructor with id only
        usage.setPromoCode(code);
        usage.setUsageStatus(PromoUsage.UsageStatus.USED);
    
        promoUsageRepository.save(usage);
    
        // Return the promo code DTO
        return new PromoCodeDto(code.getId(), code.getCode(), code.getDiscountPercentage(), code.getIsActive().toString());
    }

    public Optional<PromoCode> getPromoByCode(String promoCode) {
        // Query the promo code in the database
        return promoCodeRepository.findByCode(promoCode);
    }

    public PromoCodeDto createPromoCode(String code, Double discountPercentage) throws Exception {
        // Check if the promo code already exists
        Optional<PromoCode> existingCode = promoCodeRepository.findByCode(code);
        if (existingCode.isPresent()) {
            throw new Exception("Promo code already exists");
        }
    
        // Create a new PromoCode object
        PromoCode promoCode = new PromoCode();
        promoCode.setCode(code);
        promoCode.setDiscountPercentage(discountPercentage);
        promoCode.setIsActive(PromoCode.IsActive.ACTIVE); // Set the promo code to active by default
    
        // Save the promo code in the repository
        promoCodeRepository.save(promoCode);
    
        // Create a PromoUsage entry for each user (optional: add logic to create only for specific users)
        List<User> allUsers = userRepository.findAll(); // Fetch all users or filter as needed
        for (User user : allUsers) {
            PromoUsage promoUsage = new PromoUsage();
            promoUsage.setUser(user); // Associate with the user
            promoUsage.setPromoCode(promoCode); // Associate with the new promo code
            promoUsage.setUsageStatus(PromoUsage.UsageStatus.NOT_USED); 
    
            // Save the new promo usage record for each user
            promoUsageRepository.save(promoUsage);
        }
    
        // Send the promo code to all subscribed users (if required)
        sendPromoEmailToSubscribedUsers(promoCode);
    
        // Return the PromoCodeDto object
        return new PromoCodeDto(promoCode.getId(), promoCode.getCode(), promoCode.getDiscountPercentage(), promoCode.getIsActive().toString());
    }
    
    
    private void sendPromoEmailToSubscribedUsers(PromoCode promoCode) {
        // Get all users who are subscribed (PromoStatus.SUBSCRIBED)
        List<User> subscribedUsers = userRepository.findByPromoStatus(User.PromoStatus.SUBSCRIBED);

        // Send email to each subscribed user
        for (User user : subscribedUsers) {
            String emailSubject = "New Promo Code: " + promoCode.getCode();
            String emailBody = "Hello " + user.getFirstname() + ",\n\n" +
                                "We have a new promo code for you! Use code: " + promoCode.getCode() +
                                " to get a discount of " + promoCode.getDiscountPercentage() + "% off your next booking.\n\n" +
                                "Enjoy the savings!";
            emailService.sendEmail(user.getEmail(), emailSubject, emailBody);
        }
    }

    public List<PromoCode> getAvailablePromos(Long userId) {
        return promoUsageRepository.findByUserIdAndUsageStatus(userId, PromoUsage.UsageStatus.NOT_USED)
            .stream()
            .map(PromoUsage::getPromoCode)
            .filter(promoCode -> promoCode.getIsActive() == PromoCode.IsActive.ACTIVE)
            .collect(Collectors.toList());
    }

    public Optional<PromoCode> getPromoCodeByCode(String code) {
        return promoCodeRepository.findByCode(code);
    }
    

    public PromoUsageDto trackPromoUsage(Long userId, Long promoCodeId) {
        PromoUsage promoUsage = promoUsageRepository.findByUserIdAndPromoCodeId(userId, promoCodeId)
                .orElseThrow(() -> new RuntimeException("Promo usage not found for the given user and promo code"));
    
        return new PromoUsageDto(
                promoUsage.getId(),
                promoUsage.getUser().getId(),
                promoUsage.getPromoCode().getId(),
                promoUsage.getUsageStatus().toString()
        );
    }
    
}