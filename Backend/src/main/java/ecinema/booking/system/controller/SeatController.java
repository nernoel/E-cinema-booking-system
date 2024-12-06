package ecinema.booking.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ecinema.booking.system.dto.SeatDto;
import ecinema.booking.system.service.SeatService;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    // Get all seats for a given showtime
    @GetMapping("/{showtimeId}")
    public ResponseEntity<List<SeatDto>> getSeats(@PathVariable Long showtimeId) {
        List<SeatDto> seats = seatService.getSeatsForShowtime(showtimeId);
        return ResponseEntity.ok(seats);
    }

    // Update seat availability (book or cancel seat)
    @PutMapping("/{seatId}")
    public ResponseEntity<SeatDto> updateSeat(@PathVariable Long seatId, @RequestParam boolean isAvailable) {
        try {
            SeatDto updatedSeat = seatService.updateSeatAvailability(seatId, isAvailable);
            return ResponseEntity.ok(updatedSeat);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Add multiple seats to a showtime
@PostMapping("/create-seats")
public ResponseEntity<List<SeatDto>> addSeats(@RequestBody List<SeatDto> seatDTOs) {
    try {
        List<SeatDto> savedSeats = seatService.saveSeats(seatDTOs);
        return ResponseEntity.ok(savedSeats);
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }
}

}