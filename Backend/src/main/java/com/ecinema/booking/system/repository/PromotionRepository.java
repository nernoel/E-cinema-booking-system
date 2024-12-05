package ecinema.booking.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecinema.booking.system.entity.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    //
}