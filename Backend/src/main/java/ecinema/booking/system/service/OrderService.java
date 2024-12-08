package ecinema.booking.system.service;

import ecinema.booking.system.dto.OrderDto;

import java.util.List;

public interface OrderService {
    // Create a new order
    OrderDto createOrder(OrderDto orderDto);
    
    // Get order by id
    OrderDto getOrderById(Long orderId);

    // Get all orders 
    List<OrderDto> getAllOrders();

    // Get all orders by user
    List<OrderDto> getOrdersByUserId(Long userId);

    // Send purchase confirmation email
    void sendOrderConfirmation(OrderDto orderDto);
}
