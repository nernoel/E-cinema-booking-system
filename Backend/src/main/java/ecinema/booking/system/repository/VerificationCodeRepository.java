package ecinema.booking.system.repository;

import ecinema.booking.system.entity.VerificationCode;
import jakarta.transaction.Transactional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    // Query to find a VerificationCode by email
    Optional<VerificationCode> findByEmail(String email);

    @Modifying
    @Transactional
    // Query to delete a VerificationCode by email
    void deleteByEmail(String email);
}
