package ecinema.booking.system.service;

import java.util.stream.Collectors;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ecinema.booking.system.dto.SeatDto;
import ecinema.booking.system.dto.ShowroomDto;
import ecinema.booking.system.entity.Seat;
import ecinema.booking.system.entity.Showtime;
import ecinema.booking.system.repository.SeatRepository;
import ecinema.booking.system.repository.ShowtimeRepository;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public SeatServiceImpl(SeatRepository seatRepository, ShowtimeRepository showtimeRepository, ModelMapper modelMapper) {
        this.seatRepository = seatRepository;
        this.showtimeRepository = showtimeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SeatDto> getSeatsForShowtime(Long showtimeId) {
        List<Seat> seats = seatRepository.findByShowtimeId(showtimeId);
        return seats.stream()
                .map(seat -> modelMapper.map(seat, SeatDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SeatDto updateSeatAvailability(Long seatId, boolean isAvailable) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        seat.setAvailable(isAvailable);
        seatRepository.save(seat);

        return modelMapper.map(seat, SeatDto.class);
    }

    @Override
public List<SeatDto> saveSeats(List<SeatDto> seatDtos) {
    // Fetch the Showtime from each SeatDto's showtimeId
    List<Seat> seats = seatDtos.stream().map(dto -> {
        Showtime showtime = showtimeRepository.findById(dto.getShowtimeId())
                .orElseThrow(() -> new RuntimeException("Showtime not found"));
        
        Seat seat = new Seat();
        seat.setSeatNumber(dto.getSeatNumber());
        seat.setAvailable(dto.isAvailable());
        seat.setShowtime(showtime);
        return seat;
    }).collect(Collectors.toList());

    // Save all the seats at once
    seatRepository.saveAll(seats);

    // Return the saved seats as DTOs
    return seats.stream()
            .map(seat -> modelMapper.map(seat, SeatDto.class))
            .collect(Collectors.toList());
}

}