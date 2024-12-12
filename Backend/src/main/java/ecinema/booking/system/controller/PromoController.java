package ecinema.booking.system.controller;

import ecinema.booking.system.dto.PromoCodeDto;
import ecinema.booking.system.dto.PromoUsageDto;
import ecinema.booking.system.entity.PromoCode;
import ecinema.booking.system.entity.PromoUsage;
import ecinema.booking.system.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/promos")
public class PromoController {

    @Autowired
    private PromoService promoService;

    // Endpoint to apply a promo code
    @PostMapping("/apply")
    public ResponseEntity<String> applyPromoCode(@RequestParam Long userId, @RequestParam String promoCode) {
        try {
            promoService.applyPromoCode(userId, promoCode);
            return ResponseEntity.ok("Promo code applied and email sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    // Endpoint to get the usage details of a promo code
    @GetMapping("/usage/{userId}/{promoCodeId}")
    public ResponseEntity<?> trackPromoUsage(@PathVariable Long userId, @PathVariable Long promoCodeId) {
        try {
            PromoUsageDto promoUsageDto = promoService.trackPromoUsage(userId, promoCodeId);
            return ResponseEntity.ok(promoUsageDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    // Endpoint to create a new promo code
    @PostMapping("/create-promo-code")
    public ResponseEntity<?> createPromoCode(@RequestBody PromoCodeDto promoCodeDto) {
        try {
            PromoCodeDto createdPromoCode = promoService.createPromoCode(promoCodeDto.getCode(), promoCodeDto.getDiscountPercentage());
            return ResponseEntity.status(201).body(createdPromoCode);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    // Endpoint to get a promo code by its code
    @GetMapping("/get-promo/{code}")
    public ResponseEntity<?> getPromoCodeByCode(@PathVariable String code) {
        Optional<PromoCode> promoCode = promoService.getPromoCodeByCode(code);

        if (promoCode.isPresent()) {
            return ResponseEntity.ok(promoCode.get());
        } else {
            return ResponseEntity.status(404).body("Promo code not found");
        }
    }
}
