package ecinema.booking.system.service;

import ecinema.booking.system.dto.MovieDto;
import ecinema.booking.system.dto.PromotionDto;
import ecinema.booking.system.dto.UserDto;
import ecinema.booking.system.entity.Movie;
import ecinema.booking.system.entity.Promotion;
import ecinema.booking.system.entity.User;
import ecinema.booking.system.repository.MovieRepository;
import ecinema.booking.system.repository.PromotionRepository;
import ecinema.booking.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    // All-args constructor
    public PromotionServiceImpl(PromotionRepository promotionRepository, ModelMapper modelMapper) {
        this.promotionRepository = promotionRepository;
        this.modelMapper = modelMapper;
    }

     
    // Create a new promotion
    @Override
    public PromotionDto createPromotion(PromotionDto promotionDto) {
        Promotion promotion = new Promotion();

        promotion.setTitle(promotionDto.getTitle());

        promotion.setDescription(promotionDto.getDescription());

        promotion.setStartDate(promotionDto.getStartDate());

        promotion.setEndDate(promotionDto.getEndDate());

        promotionRepository.save(promotion);

        // Get users who are subscribed to promotions
        List<User> subscribedUsers = userRepository.findByPromoStatus(User.PromoStatus.SUBSCRIBED);

        // Send emails to subscribed users
        emailService.sendPromotionEmail(promotion, subscribedUsers);
        
       return modelMapper.map(promotion, PromotionDto.class);
    }


    // Get all the promotions
    @Override
    public List<PromotionDto> getAllPromotions() {
        List<Promotion> promotions = promotionRepository.findAll();

        // Use ModelMapper to map each Movie to MovieDto
        return promotions.stream()
                .map(promotion -> modelMapper.map(promotion, PromotionDto.class))
                .collect(Collectors.toList());
    }


    
}