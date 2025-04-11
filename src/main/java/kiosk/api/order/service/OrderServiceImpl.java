package kiosk.api.order.service;

import kiosk.api.order.repository.OrderDetailRepository;
import kiosk.api.order.domain.entity.OrderEntity;
import kiosk.api.order.domain.internal.OrderDTO;
import kiosk.api.order.domain.dto.request.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDailyResponse;
import kiosk.api.order.repository.OrderRepository;
import kiosk.api.order.domain.dto.request.OrderCreateRequest;
import kiosk.api.order.domain.dto.response.OrderResponse;
import kiosk.api.order.service.detaileLogic.OrderFactory;
import kiosk.api.order.service.detaileLogic.OrderSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    private final OrderFactory orderFactory;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {
        OrderEntity order = orderFactory.create(request);

        orderRepository.save(order);
        orderDetailRepository.saveAll(order.getOrderDetails());

        return OrderResponse.of(order, order.getOrderDetails());
    }

    @Override
    public OrderDailyResponse getDailyOrder(OrderDateRequest request) {
        LocalDateTime start = request.getCreatedAt().atStartOfDay();
        LocalDateTime end = request.getCreatedAt().plusDays(1).atStartOfDay();

        List<OrderDTO> order = orderRepository.findDailyOrder(start, end);
        OrderSummary orderSummary = OrderSummary.calculateTotal(order);

        return new OrderDailyResponse(orderSummary.getTotalPrice(), orderSummary.getTotalQuantity(), request.getCreatedAt());
    }

}
