/*
 * Payment Card repository to interact with the database
 * Extends JPA imported repository
 */

 package ecinema.booking.system.repository;

 import ecinema.booking.system.entity.PaymentCard;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
 
 public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {
    List<PaymentCard> findByUserId(Long userId);
 }
 