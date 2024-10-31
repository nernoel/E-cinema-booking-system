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

    private String number;

    private String name;

    private String securityCode;

    private String expiryDate;

    private String cardType;

}
