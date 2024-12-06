package ecinema.booking.system.service;

import ecinema.booking.system.dto.ShowroomDto;
import ecinema.booking.system.entity.Showroom;
import ecinema.booking.system.entity.Showtime;
import ecinema.booking.system.repository.ShowroomRepository;
import ecinema.booking.system.repository.ShowtimeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowroomServiceImpl implements ShowroomService {

    private final ShowroomRepository showroomRepository;
    private final ShowtimeRepository showtimeRepository; // Added repository to fetch Showtime entities
    private final ModelMapper modelMapper;

    public ShowroomServiceImpl(ShowroomRepository showroomRepository, ShowtimeRepository showtimeRepository, ModelMapper modelMapper) {
        this.showroomRepository = showroomRepository;
        this.showtimeRepository = showtimeRepository;
        this.modelMapper = modelMapper;
    }

    // Create a showroom
    @Override
    public ShowroomDto createShowroom(ShowroomDto showroomDto) {
        Showroom showroom = new Showroom();

        showroom.setName(showroomDto.getName());
        showroom.setCapacity(showroomDto.getCapacity());

        // Map showtimeIds to Showtime entities
        List<Showtime> showtimes = showroomDto.getShowtimeIds().stream()
                .map(id -> showtimeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Showtime not found with id: " + id)))
                .collect(Collectors.toList());
        showroom.setShowtimes(showtimes);

        // Save showroom and return the saved entity as DTO
        showroom = showroomRepository.save(showroom);

        return modelMapper.map(showroom, ShowroomDto.class);
    }

    // Get all showrooms
    @Override
    public List<ShowroomDto> getAllShowrooms() {
        List<Showroom> showrooms = showroomRepository.findAll();

        // Map each entity to its corresponding DTO
        return showrooms.stream()
                .map(showroom -> {
                    ShowroomDto showroomDto = modelMapper.map(showroom, ShowroomDto.class);

                    // Convert Showtimes to their IDs for DTO
                    List<Long> showtimeIds = showroom.getShowtimes().stream()
                            .map(Showtime::getId)
                            .collect(Collectors.toList());
                    showroomDto.setShowtimeIds(showtimeIds);

                    return showroomDto;
                })
                .collect(Collectors.toList());
    }
}