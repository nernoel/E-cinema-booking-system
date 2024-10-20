package com.movies.ecinema.repository;

import com.movies.ecinema.entity.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {
    // You can add custom query methods here if needed
}
