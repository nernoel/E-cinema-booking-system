package ecinema.booking.system.service;

import ecinema.booking.system.dto.ShowtimeDto;
import ecinema.booking.system.entity.Movie;
import ecinema.booking.system.entity.Showroom;
import ecinema.booking.system.entity.Showtime;
import ecinema.booking.system.repository.MovieRepository;
import ecinema.booking.system.repository.ShowroomRepository;
import ecinema.booking.system.repository.ShowtimeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowtimeServiceImpl implements ShowtimeService {

    private final MovieRepository movieRepository;
    private final ShowroomRepository showroomRepository;
    private final ShowtimeRepository showtimeRepository;
    private final ModelMapper modelMapper;

    public ShowtimeServiceImpl(MovieRepository movieRepository, 
                               ShowroomRepository showroomRepository, 
                               ShowtimeRepository showtimeRepository, 
                               ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.showroomRepository = showroomRepository;
        this.showtimeRepository = showtimeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String scheduleMovie(Long movieId, Long showroomId, String dateTime) {
        // Validate Movie
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));

        // Validate Showroom
        Showroom showroom = showroomRepository.findById(showroomId)
                .orElseThrow(() -> new RuntimeException("Showroom not found with id: " + showroomId));

        // Parse and validate DateTime
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(dateTime);
        } catch (Exception e) {
            throw new RuntimeException("Invalid date-time format. Expected format: yyyy-MM-ddTHH:mm:ss");
        }

        // Check for conflicts
        boolean conflictExists = !showtimeRepository.findByShowroomIdAndDateTime(showroomId, localDateTime).isEmpty();
        if (conflictExists) {
            return "Scheduling conflict! The showroom is already booked at this time.";
        }

        // Create and save new Showtime
        Showtime newShowtime = new Showtime();
        newShowtime.setMovie(movie);
        newShowtime.setShowroom(showroom);
        newShowtime.setDateTime(localDateTime);

        showtimeRepository.save(newShowtime);
        return "Movie scheduled successfully!";
    }

    @Override
    public List<ShowtimeDto> getAllShowtimes() {
        List<Showtime> showtimes = showtimeRepository.findAll();
        return showtimes.stream()
                .map(showtime -> {
                    ShowtimeDto dto = modelMapper.map(showtime, ShowtimeDto.class);
                    dto.setShowroomId(showtime.getShowroom().getId());
                    dto.setMovieId(showtime.getMovie().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowtimeDto> getShowtimesByMovieId(long movieId) {
        List<Showtime> showtimes = showtimeRepository.findByMovieId(movieId);
        return showtimes.stream()
                .map(showtime -> {
                    ShowtimeDto dto = modelMapper.map(showtime, ShowtimeDto.class);
                    dto.setShowroomId(showtime.getShowroom().getId());
                    dto.setMovieId(showtime.getMovie().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}