package ecinema.booking.system.service;

import ecinema.booking.system.entity.VerificationCode;
import ecinema.booking.system.repository.UserRepository;
import ecinema.booking.system.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import ecinema.booking.system.entity.Order;
import ecinema.booking.system.entity.Ticket;
import ecinema.booking.system.entity.User;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private UserRepository userRepository;

    private String senderAddress = "nnernoel@gmail.com";

    public void sendEmail(String recipientEmail, String subject, String messageContent) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderAddress);
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(messageContent);
        emailSender.send(message);
    }

    public void sendPromoAddedEmail(String email, String promoCode) {
        String subject = "Promo Code Added to Your Account";
        String messageContent = "Dear User,\n\nA new promo code (" + promoCode + ") has been added to your account. Enjoy your discount!\n\nBest regards,\nE-Cinema Team";
        sendEmail(email, subject, messageContent);
    }

    public void sendPromoEmailToSubscribedUsers(List<User> subscribedUsers, String promoCode, Double discountPercentage) {
        String subject = "New Promo Code: " + promoCode;
        String messageContent = "Hello,\n\nWe have a new promo code for you! Use code: " + promoCode + " to get a discount of " + discountPercentage + "% off your next booking.\n\nEnjoy the savings!\n\nBest regards,\nE-Cinema Team";
        
        for (User user : subscribedUsers) {
            sendEmail(user.getEmail(), subject, messageContent);
        }
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
        sendEmail(recipientEmail, "Confirmation Email", messageContent);
    }

    public void sendOrderConfirmationEmail(Order order, User user) {
        StringBuilder ticketsInfo = new StringBuilder("Tickets: \n");
        for (Ticket ticket : order.getTickets()) {
            ticketsInfo.append("Ticket ID: ").append(ticket.getId())
                       .append(" Ticket type: ").append(ticket.getTicketType())
                       //.append(", Seat Number: ").append(ticket.getSeat().getSeatNumber())
                       .append(", Price: ").append(ticket.getPrice())
                       .append("\n");
        }
    
        // Check if the payment card or card number is null
        ///String paymentInfo = "Payment Information: ";
        //String lastFourDigits = "N/A";  // Default value in case the card number is null
        
        /* 
        if (order.getPaymentCard() != null && order.getPaymentCard().getCardNumber() != null) {
            // Only access cardNumber if it's not null
            String cardNumber = order.getPaymentCard().getCardNumber();
            if (cardNumber.length() >= 4) {
                lastFourDigits = cardNumber.substring(cardNumber.length() - 4);
            }
            paymentInfo = "Card ending in " + lastFourDigits + " was used for payment";
        } else {
            paymentInfo = "No payment card information available.";
        }
            */
    
        String subject = "Order Confirmation - eCinema";
        String messageContent = "Dear " + user.getFirstname() + ",\n\n"
                + "Thank you for your order! Here are the details:\n\n"
                + "Order ID: " + order.getId() + "\n"
                + "Movie: " + order.getMovie().getTitle() + "\n"
                + "Tickets: " + ticketsInfo.toString() + "\n"
                + "Total Amount: $" + order.getOrderPrice() + "\n"
                //+ "Order Date: " + order.getOrderDate() + "\n\n"
                + order.getPaymentCard()+ "\n\n"
                + "Enjoy your movie!\n"
                + "Best Regards,\n"
                + "The eCinema Team";
    
        sendEmail(user.getEmail(), subject, messageContent);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}