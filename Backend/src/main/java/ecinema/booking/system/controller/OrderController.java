package ecinema.booking.system.controller;

import ecinema.booking.system.dto.OrderDto;
import ecinema.booking.system.dto.UserDto;
import ecinema.booking.system.entity.Order;
import ecinema.booking.system.entity.User;
import ecinema.booking.system.service.EmailService;
import ecinema.booking.system.service.OrderService;
import ecinema.booking.system.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(
            OrderService orderService,
            UserService userService,
            EmailService emailService,
            ModelMapper modelMapper) {
        this.orderService = orderService;
        this.userService = userService;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/confirm")
    public ResponseEntity<OrderDto> confirmOrder(@RequestBody OrderDto orderDto) {
        if (orderDto.getUserId() == null || orderDto.getMovieId() == null /*|| orderDto.getTickets() <= 0 */) {
            return ResponseEntity.badRequest().build();
        }

        OrderDto createdOrder = orderService.createOrder(orderDto);

        UserDto userDto = userService.findUserById(orderDto.getUserId());
        User user = modelMapper.map(userDto, User.class);
        Order order = modelMapper.map(createdOrder, Order.class);

        emailService.sendOrderConfirmationEmail(order, user);

        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersForUser(@PathVariable Long userId) {
        List<OrderDto> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
}
