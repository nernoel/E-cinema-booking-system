package ecinema.booking.system.service;

import ecinema.booking.system.dto.PaymentCardDto;
import ecinema.booking.system.entity.PaymentCard;
import ecinema.booking.system.repository.PaymentCardRepository;
import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentCardServiceImpl implements PaymentCardService {

    private final PaymentCardRepository paymentCardRepository;
    private final ModelMapper modelMapper;

    public PaymentCardServiceImpl(PaymentCardRepository paymentCardRepository, ModelMapper modelMapper) {
        this.paymentCardRepository = paymentCardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentCardDto addPaymentCard(PaymentCardDto paymentCardDto) {
        // Map DTO to Entity
        PaymentCard paymentCard = modelMapper.map(paymentCardDto, PaymentCard.class);
        // Save entity
        PaymentCard savedCard = paymentCardRepository.save(paymentCard);
        // Map Entity to DTO
        return modelMapper.map(savedCard, PaymentCardDto.class);
    }

    @Override
    public PaymentCardDto getPaymentCardById(Long cardId) {
        // Fetch card from repository
        PaymentCard paymentCard = paymentCardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Payment card not found with ID: " + cardId));
        
        // Map entity to DTO
        return modelMapper.map(paymentCard, PaymentCardDto.class);
    }

    @Override
    public List<PaymentCardDto> getPaymentCardsByUserId(Long userId) {
        // Retrieve entities
        List<PaymentCard> paymentCards = paymentCardRepository.findByUserId(userId);
        // Map Entities to DTOs
        return paymentCards.stream()
                .map(card -> modelMapper.map(card, PaymentCardDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePaymentCard(Long cardId) {
        paymentCardRepository.deleteById(cardId);
    }
}