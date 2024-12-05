package ecinema.booking.system.service;

import java.util.List;

import ecinema.booking.system.dto.SeatDto;

public interface SeatService {
    List<SeatDto> getSeatsForShowtime(Long showtimeId);
    SeatDto updateSeatAvailability(Long seatId, boolean isAvailable) throws Exception;
    List<SeatDto> saveSeats(List<SeatDto> seatDtos);
}