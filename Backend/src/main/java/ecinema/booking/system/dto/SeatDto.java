package ecinema.booking.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ecinema.booking.system.entity.Seat.SeatStatus;

public class SeatDto {

    private Long id;
    private String seatNumber;

    @JsonProperty("seatStatus")  
    private SeatStatus seatStatus; 

    private Long showtimeId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public Long getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Long showtimeId) {
        this.showtimeId = showtimeId;
    }
}
