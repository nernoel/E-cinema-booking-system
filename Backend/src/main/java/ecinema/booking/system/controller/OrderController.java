package ecinema.booking.system.controller;

import ecinema.booking.system.dto.OrderDto;
import ecinema.booking.system.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        if (orderDto.getUserId() == null || orderDto.getMovieId() == null || orderDto.getPaymentCardId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        OrderDto createdOrder = orderService.createOrder(orderDto);

         // Email handling is now within the service layer
         orderService.sendOrderConfirmation(createdOrder);
         return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersForUser(@PathVariable Long userId) {
        List<OrderDto> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/get-order/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}