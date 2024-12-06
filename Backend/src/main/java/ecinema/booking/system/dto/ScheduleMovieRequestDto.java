package ecinema.booking.system.dto;

public class ScheduleMovieRequestDto {
    private Long movieId;
    private Long showroomId;
    private String dateTime;

    // Getters and setters
    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getShowroomId() {
        return showroomId;
    }

    public void setShowroomId(Long showroomId) {
        this.showroomId = showroomId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}