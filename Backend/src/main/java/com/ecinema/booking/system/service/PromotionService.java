package ecinema.booking.system.service;

import java.util.List;

import ecinema.booking.system.dto.PromotionDto;


public interface PromotionService {
    // Create a new promotion
    PromotionDto createPromotion(PromotionDto promotionDto);

    // Get all promotions
    List<PromotionDto> getAllPromotions();
    
    
}