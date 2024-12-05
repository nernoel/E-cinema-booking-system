package ecinema.booking.system.controller;

import ecinema.booking.system.dto.ScheduleMovieRequestDto;
import ecinema.booking.system.dto.ShowroomDto;
import ecinema.booking.system.dto.ShowtimeDto;
import ecinema.booking.system.service.ShowtimeService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @PostMapping("/schedule-movie")
    public ResponseEntity<String> scheduleMovie(@RequestBody ScheduleMovieRequestDto request) {
        LocalDateTime dateTime = LocalDateTime.parse(request.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        try {
            String message = showtimeService.scheduleMovie(request.getMovieId(), request.getShowroomId(), request.getDateTime());
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-showtimes")
    public ResponseEntity<List<ShowtimeDto>> getAllShowtimes() {
        return ResponseEntity.ok(showtimeService.getAllShowtimes());
    }

    @GetMapping("/get-showtimes/{movieId}")
    public ResponseEntity<List<ShowtimeDto>> getShowtimesByMovieId(@PathVariable long movieId) {
        return ResponseEntity.ok(showtimeService.getShowtimesByMovieId(movieId));
    }
}