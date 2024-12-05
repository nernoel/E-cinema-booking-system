package ecinema.booking.system.repository;

import ecinema.booking.system.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    
    List<Showtime> findByMovieId(Long movieId);

    List<Showtime> findByShowroomIdAndDateTime(Long showroomId, LocalDateTime dateTime);

    
}