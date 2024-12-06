package ecinema.booking.system.service;

import java.util.List;

import ecinema.booking.system.dto.ShowroomDto;

public interface ShowroomService {
    ShowroomDto createShowroom(ShowroomDto showroomDto);
    List<ShowroomDto> getAllShowrooms();
    
} 