package ecinema.booking.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SeatDto {

    private Long id;

    private String seatNumber;

    @JsonProperty("isAvailable") 
    private boolean isAvailable;

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

    public boolean isAvailable() {
        return isAvailable;
    }
    

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Long getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Long showtimeId) {
        this.showtimeId = showtimeId;
    }
}