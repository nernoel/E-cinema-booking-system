package ecinema.booking.system.service;

import ecinema.booking.system.dto.OrderDto;
import ecinema.booking.system.dto.TicketDto;
import ecinema.booking.system.entity.*;
import ecinema.booking.system.repository.*;
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
    private final PaymentCardRepository paymentCardRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            UserRepository userRepository,
            MovieRepository movieRepository,
            EmailService emailService,
            ModelMapper modelMapper,
            TicketRepository ticketRepository,
            ShowtimeRepository showtimeRepository,
            SeatRepository seatRepository,
            PaymentCardRepository paymentCardRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
        this.ticketRepository = ticketRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
        this.paymentCardRepository = paymentCardRepository;
    }

    @Transactional
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Movie movie = movieRepository.findById(orderDto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        PaymentCard paymentCard = paymentCardRepository.findById(orderDto.getPaymentCardId())
                .orElseThrow(() -> new RuntimeException("Payment card not found"));

        Order order = new Order();
        order.setUser(user);
        order.setMovie(movie);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderPrice(orderDto.getOrderPrice());
        order.setPaymentCard(paymentCard);

        orderRepository.save(order);

        List<Ticket> tickets = createTicketsForOrder(order, orderDto.getTickets());
        order.setTickets(tickets);
        ticketRepository.saveAll(tickets);

        return modelMapper.map(order, OrderDto.class);
    }

    private List<Ticket> createTicketsForOrder(Order order, List<TicketDto> ticketDtos) {
        List<Ticket> tickets = new ArrayList<>();
        for (TicketDto ticketDto : ticketDtos) {
            Ticket ticket = new Ticket();
            ticket.setOrder(order);
            ticket.setPrice(ticketDto.getTicketPrice());
            ticket.setTicketType(Ticket.TicketType.valueOf(ticketDto.getTicketType().toUpperCase()));

            User user = userRepository.findById(ticketDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Showtime showtime = showtimeRepository.findById(ticketDto.getShowtimeId())
                    .orElseThrow(() -> new RuntimeException("Showtime not found"));
            Seat seat = seatRepository.findById(ticketDto.getSeatId())
                    .orElseThrow(() -> new RuntimeException("Seat not found"));

            ticket.setUser(user);
            ticket.setShowtime(showtime);
            ticket.setSeat(seat);
            ticket.setSeatStatus(Seat.SeatStatus.TAKEN);

            tickets.add(ticket);
        }
        return tickets;
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> modelMapper.map(order, OrderDto.class))
                .orElse(null);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void sendOrderConfirmation(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Order order = modelMapper.map(orderDto, Order.class);
        emailService.sendOrderConfirmationEmail(order, user);
    }
}
