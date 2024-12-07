package ecinema.booking.system.service;

import ecinema.booking.system.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getOrdersByUserId(Long userId);
    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderById(Long orderId);
}
