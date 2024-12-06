package ecinema.booking.system.controller;

import ecinema.booking.system.dto.ShowroomDto;
import ecinema.booking.system.service.ShowroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/showrooms")
public class ShowroomController {

    private final ShowroomService showroomService;

    @Autowired
    public ShowroomController(ShowroomService showroomService) {
        this.showroomService = showroomService;
    }
    // Create a new showroom
    @PostMapping("/create-showroom")
    public ShowroomDto createShowroom(@RequestBody ShowroomDto showroomDto) {
        return showroomService.createShowroom(showroomDto);
    }

    // Get all showrooms
    @GetMapping("/get-showrooms")
    public List<ShowroomDto> getAllShowrooms() {
        return showroomService.getAllShowrooms();
    }
}