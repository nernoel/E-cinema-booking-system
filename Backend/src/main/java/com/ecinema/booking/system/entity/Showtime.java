package ecinema.booking.system.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;



@Entity
@Table(name = "showtimes")
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "showroom_id", nullable = false)
    @JsonBackReference("showtime-showroom")
    private Showroom showroom;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonBackReference
    private Movie movie;

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL)
    private List<Seat> seats;

    // No-Args Constructor
    public Showtime() {
    }

    // All-Args Constructor
    public Showtime(long id, LocalDateTime dateTime, Showroom showroom, Movie movie, List<Seat> seats) {
        this.id = id;
        this.dateTime = dateTime;
        this.showroom = showroom;
        this.movie = movie;
        this.seats = seats;
    }

    

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    // Update this setter to accept LocalDateTime
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Showroom getShowroom() {
        return showroom;
    }

    public void setShowroom(Showroom showroom) {
        this.showroom = showroom;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}