package ecinema.booking.system.service;

import org.springframework.stereotype.Service;

import ecinema.booking.system.dto.PromotionDto;
import ecinema.booking.system.entity.Promotion;
import ecinema.booking.system.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    //@Value("")
    private String senderAddress = "EMAIL_ADDRESS";

    public void sendPromotionEmail(Promotion promotion, List<User> users) {
        for (User user : users) {
            if (user.getPromoStatus() == User.PromoStatus.SUBSCRIBED) {
                sendEmail(user.getEmail(), promotion.getTitle(), promotion.getDescription(), promotion.getStartDate(), promotion.getEndDate());
            }
        }
    }

    private void sendEmail(String recipientEmail, String subject, String messageContent, LocalDateTime localDateTime, LocalDateTime localDateTime2) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderAddress);
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(messageContent);

        emailSender.send(message);
    }

    /* 
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
    */


    public String sendConfirmationEmail(String recipientEmail) {
        String verificationCode = generateVerificationCode();
        String messageContent = "This is your confirmation email. Your verification code is: " + verificationCode;

        /*
         * Create message to send to the user
         */
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderAddress);
        message.setTo(recipientEmail);
        message.setSubject("Confirmation Email");
        message.setText(messageContent);

        emailSender.send(message);

        return verificationCode;
    }

    /*
     * Generate a verification code to use
     */
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); 
        return String.valueOf(code);
    }
}