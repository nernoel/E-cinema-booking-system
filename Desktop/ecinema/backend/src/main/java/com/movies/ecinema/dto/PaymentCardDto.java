package com.movies.ecinema.dto;

import com.movies.ecinema.entity.PaymentCard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCardDto {

    private long id;

    private String cardNumber;

    private String cardHolderName;

    private String expiryDate;
    
    private String cardType;

    // Constructor to create PaymentCardDTO from PaymentCard entity
    public PaymentCardDto(PaymentCard paymentCard) {
        this.id = paymentCard.getId();

        this.cardNumber = paymentCard.getCardNumber();

        this.cardHolderName = paymentCard.getCardHolderName();

        this.expiryDate = paymentCard.getExpiryDate();

        this.cardType = paymentCard.getCardType();
    }
    
}
