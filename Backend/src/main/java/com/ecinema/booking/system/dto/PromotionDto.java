package ecinema.booking.system.dto;

import java.time.LocalDateTime;
import java.util.List;

import ecinema.booking.system.entity.User;

public class PromotionDto {

    private long id;

    private String title;

    private String description;

    private LocalDateTime startDate;
    
    private LocalDateTime endDate;

    private List<User> users;


    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setUsers(List<User> users){
        this.users = users;
    }

    public List<User> getUsers(){
        return users;
    }
}