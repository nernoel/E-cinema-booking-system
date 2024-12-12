package ecinema.booking.system.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ecinema.booking.system.entity.VerificationCode;



import ecinema.booking.system.service.VerificationService;
import ecinema.booking.system.service.EmailService;
import ecinema.booking.system.service.UserService;
import ecinema.booking.system.dto.EmailRequestDto;
import ecinema.booking.system.dto.VerificationRequestDto;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationService verificationService;

    @PostMapping("api/get-verification-code")
    public ResponseEntity<String> getVerificationCode(@RequestParam("email") String email) {
    // Retrieve the verification code from the database
    Optional<VerificationCode> storedCodeOptional = verificationService.findByEmail(email);

    if (storedCodeOptional.isEmpty()) {
        return ResponseEntity.status(404).body("Verification code not found for the provided email.");
    }

    VerificationCode storedCode = storedCodeOptional.get();

    return ResponseEntity.ok(storedCode.getVerificationCode());
}

    @PostMapping("api/verify-code")
public ResponseEntity<String> verifyCode(@RequestBody VerificationRequestDto verificationRequest) {
    String email = verificationRequest.getEmail();
    String verificationCode = verificationRequest.getVerificationCode();

    // Retrieve the verification code from the database
    Optional<VerificationCode> storedCodeOptional = verificationService.findByEmail(email);

    if (storedCodeOptional.isEmpty()) {
        return ResponseEntity.status(400).body("Verification code not found.");
    }

    // Get the stored code
    VerificationCode storedCode = storedCodeOptional.get();

    // Check if the code is expired
    if (verificationService.isCodeExpired(storedCode)) {
        return ResponseEntity.status(400).body("Verification code has expired.");
    }

    // Verify the code matches
    if (!storedCode.getVerificationCode().equals(verificationCode)) {
        return ResponseEntity.status(400).body("Invalid verification code.");
    }

    // Update the user status to ACTIVE
    boolean isUpdated = userService.updateUserStatusToActive(email);
    if (!isUpdated) {
        return ResponseEntity.status(500).body("Failed to update user status.");
    }

    // Delete the verification code
    verificationService.deleteVerificationCode(email);

    return ResponseEntity.ok("Verification successful. User status updated to ACTIVE.");
}

    @PostMapping("/api/send-confirmation-email")
    public ResponseEntity<?> sendConfirmationEmail(@RequestBody EmailRequestDto emailRequest) {
        // Validate the email
        if (emailRequest.getEmail() == null || emailRequest.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required.");
        }

        try {
            // Send email and get verification code
            //String verificationCode = emailService.sendConfirmationEmail(emailRequest.getEmail());

            emailService.sendConfirmationEmail(emailRequest.getEmail());

            // Store verification code in session
           // HttpSession session = request.getSession();
            //session.setAttribute(emailRequest.getEmail(), verificationCode);

            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
    }
}