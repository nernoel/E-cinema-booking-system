package com.movies.ecinema.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "payment_cards")
public class PaymentCard {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String cardNumber;

    private String cardHolderName;

    private String expiryDate;

    private String cardType;

    public PaymentCard(long id, String cardNumber, String cardHolderName, String expiryDate, String cardType,
            User user) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cardType = cardType;
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
