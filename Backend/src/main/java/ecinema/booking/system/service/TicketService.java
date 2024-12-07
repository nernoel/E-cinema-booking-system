package ecinema.booking.system.service;

import ecinema.booking.system.dto.TicketDto;
import java.util.List;

public interface TicketService {

    TicketDto createTicket(TicketDto ticketDto);

    TicketDto getTicketById(Long id);

    List<TicketDto> getAllTickets();

    void deleteTicket(Long id);
}
