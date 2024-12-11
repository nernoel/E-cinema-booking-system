package ecinema.booking.system.service;

import java.util.List;

import ecinema.booking.system.dto.SeatDto;
import ecinema.booking.system.entity.Seat.SeatStatus;

public interface SeatService {
    List<SeatDto> getSeatsForShowtime(Long showtimeId);
    SeatDto updateSeatAvailability(Long seatId, SeatStatus seatStatus);
    List<SeatDto> saveSeats(List<SeatDto> seatDtos);
    SeatDto getSeatById(Long seatId);
}