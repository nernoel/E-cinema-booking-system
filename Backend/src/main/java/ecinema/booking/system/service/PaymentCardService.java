package ecinema.booking.system.service;

import ecinema.booking.system.dto.PaymentCardDto;

import java.util.List;

public interface PaymentCardService {

    /**
     * Add a new payment card for a user.
     *
     * @param paymentCardDto the payment card data transfer object
     * @return the added payment card as a DTO
     */
    PaymentCardDto addPaymentCard(PaymentCardDto paymentCardDto);

    /**
     * Retrieve all payment cards for a given user.
     *
     * @param userId the ID of the user
     * @return a list of payment card DTOs
     */
    List<PaymentCardDto> getPaymentCardsByUserId(Long userId);

    /**
     * Delete a payment card by its ID.
     *
     * @param cardId the ID of the payment card
     */
    void deletePaymentCard(Long cardId);

    
    PaymentCardDto getPaymentCardById(Long cardId);
    
}
