package ecinema.booking.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import ecinema.booking.system.service.EmailService;
import ecinema.booking.system.service.UserService;
import ecinema.booking.system.dto.EmailRequestDto;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class EmailController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/send-confirmation-email")
    public ResponseEntity<?> sendConfirmationEmail(@RequestBody EmailRequestDto emailRequest) {
        // Validate the email
        if (emailRequest.getEmail() == null || emailRequest.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required.");
        }

        try {
            // Send email and get verification code
            String verificationCode = emailService.sendConfirmationEmail(emailRequest.getEmail());

            // Store verification code in session
            HttpSession session = request.getSession();
            session.setAttribute(emailRequest.getEmail(), verificationCode);

            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
    }

    /*
     * Vertification endpoint
     */
    @PostMapping("/api/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody EmailRequestDto emailRequest) {    
        // Validate email and verification code
        String email = emailRequest.getEmail();
        String verificationCode = emailRequest.getVerificationCode();

        if (email == null || email.isEmpty() || verificationCode == null || verificationCode.isEmpty()) {
            return ResponseEntity.badRequest().body("Email and verification code are required.");
        }

        // Retrieve verification code from session
        HttpSession session = request.getSession();
        String storedCode = (String) session.getAttribute(email);

        if (storedCode == null) {
            return ResponseEntity.status(400).body("Verification code not found. Please request a new code.");
        }

        // Compare the provided code with the stored code
        if (storedCode.equals(verificationCode)) {

        // Update the user status to ACTIVE
        boolean isUpdated = userService.updateUserStatusToActive(email);

        if (isUpdated) {
            return ResponseEntity.ok("Verification successful. User status updated to ACTIVE.");
        } else {
            return ResponseEntity.status(500).body("Failed to update user status.");
        }
        } else {
            return ResponseEntity.status(400).body("Invalid verification code.");
        }
    }
}