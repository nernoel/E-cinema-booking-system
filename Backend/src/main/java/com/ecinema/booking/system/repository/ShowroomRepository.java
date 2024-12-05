package ecinema.booking.system.repository;


import ecinema.booking.system.entity.Showroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowroomRepository extends JpaRepository<Showroom, Long> {
}