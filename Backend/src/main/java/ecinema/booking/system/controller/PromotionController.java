package ecinema.booking.system.controller;

import ecinema.booking.system.dto.PromotionDto;
import ecinema.booking.system.service.PromotionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    // Create promotion
    @PostMapping("/create-promotion")
    public ResponseEntity<PromotionDto> createPromotion(@RequestBody PromotionDto promotionDto) {
        PromotionDto newPromotion = promotionService.createPromotion(promotionDto);
        return new ResponseEntity<>(newPromotion, HttpStatus.CREATED);
    }

    // Get all promotions
    @GetMapping("/get-promotions")
    public List<PromotionDto> getAllPromotions() {
        return promotionService.getAllPromotions();
    }
    
}