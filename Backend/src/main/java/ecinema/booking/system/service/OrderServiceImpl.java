package ecinema.booking.system.service;

import ecinema.booking.system.dto.MovieDto;
import ecinema.booking.system.dto.OrderDto;
import ecinema.booking.system.dto.TicketDto;
import ecinema.booking.system.entity.Movie;
import ecinema.booking.system.entity.Order;
import ecinema.booking.system.entity.Seat;
import ecinema.booking.system.entity.Showtime;
import ecinema.booking.system.entity.Ticket;
import ecinema.booking.system.entity.User;
import ecinema.booking.system.repository.MovieRepository;
import ecinema.booking.system.repository.OrderRepository;
import ecinema.booking.system.repository.SeatRepository;
import ecinema.booking.system.repository.ShowtimeRepository;
import ecinema.booking.system.repository.TicketRepository;
import ecinema.booking.system.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final EmailService emailService;
    private final ModelMapper modelMapper;
    private final TicketRepository ticketRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;

    // All args constructor
    public OrderServiceImpl(
            OrderRepository orderRepository,
            UserRepository userRepository,
            MovieRepository movieRepository,
            EmailService emailService,
            ModelMapper modelMapper,
            TicketRepository ticketRepository,
            ShowtimeRepository showtimeRepository,
            SeatRepository seatRepository) {
        this.orderRepository = orderRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
        this.ticketRepository = ticketRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
    }

    /*
     * Create a new order with associated tickets
     */
    @Transactional
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        // Fetch the User and Movie based on IDs from the DTO
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Movie movie = movieRepository.findById(orderDto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // Create and save the order entity
        Order order = new Order();
        order.setUser(user);
        order.setMovie(movie);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderPrice(orderDto.getOrderPrice());

        // Save the order first
        orderRepository.save(order);

        // Create tickets for this order
        List<Ticket> tickets = createTicketsForOrder(order, orderDto.getTickets());

        // Associate the tickets with the order
        order.setTickets(tickets);

        // Save the tickets to the database
        ticketRepository.saveAll(tickets);

        // Return the order DTO
        return modelMapper.map(order, OrderDto.class);
    }

    // Create tickets for an order
    private List<Ticket> createTicketsForOrder(Order order, List<TicketDto> ticketDtos) {
        List<Ticket> tickets = new ArrayList<>();

        // Loop through each ticket DTO and create the corresponding Ticket entity
        for (TicketDto ticketDto : ticketDtos) {
            Ticket ticket = new Ticket();

            // Set references for the ticket
            ticket.setOrder(order);  // Associate with the order
            ticket.setPrice(ticketDto.getTicketPrice());  // Set the price from DTO

            // Set ticket type (assuming it's a string and converting to Enum)
            ticket.setTicketType(Ticket.TicketType.valueOf(ticketDto.getTicketType().toUpperCase()));

            // Fetch user, showtime, and seat based on their IDs
            User user = userRepository.findById(ticketDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Showtime showtime = showtimeRepository.findById(ticketDto.getShowtimeId())
                    .orElseThrow(() -> new RuntimeException("Showtime not found"));
            Seat seat = seatRepository.findById(ticketDto.getSeatId())
                    .orElseThrow(() -> new RuntimeException("Seat not found"));

            // Set the associations
            ticket.setUser(user);
            ticket.setShowtime(showtime);
            ticket.setSeat(seat);

            // Add ticket to the list
            tickets.add(ticket);
        }

        return tickets;
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + orderId + " not found"));
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void sendOrderConfirmation(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + orderDto.getUserId() + " not found"));
        Order order = modelMapper.map(orderDto, Order.class);
        emailService.sendOrderConfirmationEmail(order, user);
    }
}
