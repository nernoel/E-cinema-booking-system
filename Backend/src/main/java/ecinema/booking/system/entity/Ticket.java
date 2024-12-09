/** DONE **/

package ecinema.booking.system.entity;

import ecinema.booking.system.entity.Seat.SeatStatus;
import jakarta.persistence.*;

@Entity
@Table(name="tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private double ticketPrice;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    public enum TicketType {
        CHILD,
        ADULT,
        SENIOR
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public double getPrice() {
        return ticketPrice;
    }

    public void setPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    // Get the seat status from the associated seat
    public SeatStatus getSeatStatus() {
        return seat.getSeatStatus();
    }

    // Set the seat status and update the seat entity
    public void setSeatStatus(SeatStatus seatStatus) {
        seat.setSeatStatus(seatStatus);  // Update the seat status directly
    }

    @Override
    public String toString() {
        return "Ticket{id=" + id + ", seatNumber='" + seat.getSeatNumber() + "', price=" + ticketPrice + "}";
    }
}
