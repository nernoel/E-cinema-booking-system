package ecinema.booking.system.service;

import ecinema.booking.system.entity.VerificationCode;
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
import ecinema.booking.system.entity.Promotion;
import ecinema.booking.system.entity.Ticket;
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

    public void sendOrderConfirmationEmail(Order order, User user) {

        StringBuilder ticketsInfo = new StringBuilder("Tickets: \n");
        for (Ticket ticket : order.getTickets()) {
            ticketsInfo.append("Ticket ID: ").append(ticket.getId())
                       .append(" Ticket type: ").append(ticket.getTicketType())
                       .append(", Seat Number: ").append(ticket.getSeat().getSeatNumber())
                       .append(", Price: ").append(ticket.getPrice())
                       .append("\n");
        }
    
        // Check if the payment card or card number is null
        String paymentInfo = "Payment Information: ";
        String lastFourDigits = "N/A";  // Default value in case the card number is null
    
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
    
        String subject = "Order Confirmation - eCinema";
        String messageContent = "Dear " + user.getFirstname() + ",\n\n"
                + "Thank you for your order! Here are the details:\n\n"
                + "Order ID: " + order.getId() + "\n"
                + "Movie: " + order.getMovie().getTitle() + "\n"
                + "Tickets: " + ticketsInfo.toString() + "\n"
                + "Total Amount: $" + order.getOrderPrice() + "\n"
                + "Order Date: " + order.getOrderDate() + "\n\n"
                + paymentInfo + "\n\n"
                + "Enjoy your movie!\n"
                + "Best Regards,\n"
                + "The eCinema Team";
    
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderAddress);
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(messageContent);
    
        emailSender.send(message);
    }
    

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
