package ecinema.booking.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ecinema.booking.system.entity.Seat;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    
    // Find all seats for a given showtime ID
    List<Seat> findByShowtimeId(Long showtimeId);

    // Optionally, you can add a method to find a seat by its number and showtime
    Seat findBySeatNumberAndShowtimeId(String seatNumber, Long showtimeId);
}