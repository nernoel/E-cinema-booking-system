package ecinema.booking.system.service;

import java.util.List;

import ecinema.booking.system.dto.ShowtimeDto;

public interface ShowtimeService {
    String scheduleMovie(Long movieId, Long showroomId, String dateTime);
    List<ShowtimeDto> getAllShowtimes();
    public List<ShowtimeDto> getShowtimesByMovieId(long movieId);

} 
