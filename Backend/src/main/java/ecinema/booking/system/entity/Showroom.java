package ecinema.booking.system.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@Table(name = "showrooms")
public class Showroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private int capacity;

    @OneToMany(mappedBy = "showroom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("showroom-showtimes")
    private List<Showtime> showtimes;

    // No-Args Constructor
    public Showroom() {
    }

    // All-Args Constructor
    public Showroom(long id, String name, int capacity, List<Showtime> showtimes) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.showtimes = showtimes;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }
}