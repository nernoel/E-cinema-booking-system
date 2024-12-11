package ecinema.booking.system.controller;

import ecinema.booking.system.dto.PaymentCardDto;
import ecinema.booking.system.entity.PaymentCard;
import ecinema.booking.system.entity.User;
import ecinema.booking.system.service.PaymentCardService;
import ecinema.booking.system.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-cards")
public class PaymentCardController {

    private final PaymentCardService paymentCardService;
    private final UserService userService;

    public PaymentCardController(PaymentCardService paymentCardService, UserService userService) {
        this.paymentCardService = paymentCardService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<PaymentCardDto> addPaymentCard(@RequestBody PaymentCardDto paymentCardDto) {
    PaymentCardDto createdCard = paymentCardService.addPaymentCard(paymentCardDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCard);
}


    /*
     * Get all payment cards for a user by userId
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentCardDto>> getPaymentCardsByUserId(@PathVariable Long userId) {
        List<PaymentCardDto> cards = paymentCardService.getPaymentCardsByUserId(userId);
        return ResponseEntity.ok(cards);
    }

    /*
     * Delete a payment card by cardId
     */
    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> deletePaymentCard(@PathVariable Long cardId) {
        paymentCardService.deletePaymentCard(cardId);
        return ResponseEntity.ok("Payment card deleted successfully!");
    }

    /*
     * Get a specific payment card by cardId
     */
    @GetMapping("/{cardId}")
    public ResponseEntity<PaymentCardDto> getPaymentCardById(@PathVariable Long cardId) {
        PaymentCardDto card = paymentCardService.getPaymentCardById(cardId);
        return ResponseEntity.ok(card);
    }
}