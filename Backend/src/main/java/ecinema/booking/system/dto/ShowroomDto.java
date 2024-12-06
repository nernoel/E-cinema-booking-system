package ecinema.booking.system.dto;

import java.util.List;

public class ShowroomDto {

    private long id;
    private String name;
    private int capacity;
    private List<Long> showtimeIds; // Only IDs for simplicity in DTO

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

    public List<Long> getShowtimeIds() {
        return showtimeIds;
    }

    public void setShowtimeIds(List<Long> showtimeIds) {
        this.showtimeIds = showtimeIds;
    }
}