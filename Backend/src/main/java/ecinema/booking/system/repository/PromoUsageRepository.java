package ecinema.booking.system.repository;

import ecinema.booking.system.entity.PromoUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PromoUsageRepository extends JpaRepository<PromoUsage, Long> {
    Optional<PromoUsage> findByUserIdAndPromoCodeId(Long userId, Long promoCodeId);
    List<PromoUsage> findByUserIdAndUsageStatus(Long userId, PromoUsage.UsageStatus usageStatus);
}
