package ecinema.booking.system.service;

import ecinema.booking.system.repository.VerificationCodeRepository;
import jakarta.transaction.Transactional;
import ecinema.booking.system.entity.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VerificationService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    public boolean verifyCode(String email, String code) {
        // Retrieve the stored verification code
        Optional<VerificationCode> optionalCode = verificationCodeRepository.findByEmail(email);

        if (optionalCode.isEmpty()) {
            return false; // No code found for this email
        }

        VerificationCode storedCode = optionalCode.get();

        // Check if the code matches and has not expired
        if (storedCode.getVerificationCode().equals(code) && 
            storedCode.getExpiryTime().isAfter(LocalDateTime.now())) {
            // Code is valid, delete it to prevent reuse
            verificationCodeRepository.delete(storedCode);
            return true;
        }
        
        return false; // Invalid or expired code
    }


    public VerificationService(VerificationCodeRepository verificationCodeRepository) {
        this.verificationCodeRepository = verificationCodeRepository;
    }

    public Optional<VerificationCode> findByEmail(String email) {
        return verificationCodeRepository.findByEmail(email);
    }

    @Transactional
    public void deleteVerificationCode(String email) {
        verificationCodeRepository.deleteByEmail(email);
    }

    public boolean isCodeExpired(VerificationCode verificationCode) {
        return verificationCode.getExpiryTime().isBefore(LocalDateTime.now());
    }
}


