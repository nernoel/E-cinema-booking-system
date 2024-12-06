package ecinema.booking.system.service;

import ecinema.booking.system.entity.VerificationCode;
import ecinema.booking.system.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import ecinema.booking.system.entity.Promotion;
import ecinema.booking.system.entity.User;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    private String senderAddress = "nnernoel@gmail.com";

    public void sendPromotionEmail(Promotion promotion, List<User> users) {
        for (User user : users) {
            if (user.getPromoStatus() == User.PromoStatus.SUBSCRIBED) {
                sendEmail(user.getEmail(), promotion.getTitle(), promotion.getDescription(),
                        promotion.getStartDate(), promotion.getEndDate());
            }
        }
    }

    private void sendEmail(String recipientEmail, String subject, String messageContent, 
                           LocalDateTime startDate, LocalDateTime endDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderAddress);
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(messageContent);
        emailSender.send(message);
    }

    public void sendConfirmationEmail(String recipientEmail) {
        // Generate verification code
        String verificationCode = generateVerificationCode();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(10); // Code expires in 10 minutes

        // Save verification code to database
        VerificationCode code = new VerificationCode();
        code.setEmail(recipientEmail);
        code.setVerificationCode(verificationCode);
        code.setExpiryTime(expiryTime);
        verificationCodeRepository.save(code);

        // Send the email
        String messageContent = "This is your confirmation email. Your verification code is: " + verificationCode;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderAddress);
        message.setTo(recipientEmail);
        message.setSubject("Confirmation Email");
        message.setText(messageContent);

        emailSender.send(message);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
