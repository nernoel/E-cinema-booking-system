package com.movies.ecinema.repository;

import com.movies.ecinema.entity.PaymentCard;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {
    // java.util.List<PaymentCard> findByUserId(Long userId);
   // List<PaymentCard> findByUserEmail(String email);
}
