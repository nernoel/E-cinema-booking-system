package ecinema.booking.system.repository;

import ecinema.booking.system.entity.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {
    Optional<PromoCode> findByCodeAndIsActive(String code, PromoCode.IsActive isActive);
    Optional<PromoCode> findByCode(String code);
}
