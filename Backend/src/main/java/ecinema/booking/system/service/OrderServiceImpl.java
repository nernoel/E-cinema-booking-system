package ecinema.booking.system.service;

import ecinema.booking.system.dto.OrderDto;
import ecinema.booking.system.entity.Movie;
import ecinema.booking.system.entity.Order;
import ecinema.booking.system.entity.User;
import ecinema.booking.system.repository.MovieRepository;
import ecinema.booking.system.repository.OrderRepository;
import ecinema.booking.system.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            UserRepository userRepository,
            MovieRepository movieRepository,
            ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;

        // Configure ModelMapper for custom mappings
       // configureModelMapper();
    }

    private void configureModelMapper() {
        modelMapper.typeMap(Order.class, OrderDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getId(), OrderDto::setUserId);
            mapper.map(src -> src.getMovie().getId(), OrderDto::setMovieId);
            mapper.map(src -> src.getMovie().getTitle(), OrderDto::setMovieTitle);
            mapper.map(src -> src.getTickets().size(), OrderDto::setTickets);
        });
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = new Order();

        // Fetch related entities
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Movie movie = movieRepository.findById(orderDto.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        // Set fields
        order.setUser(user);
        order.setMovie(movie);
        order.setOrderDate(orderDto.getOrderDate());
        order.setTotalAmount(orderDto.getTotalAmount());

        // Save the order
        Order savedOrder = orderRepository.save(order);

        return modelMapper.map(savedOrder, OrderDto.class);
    }
}
