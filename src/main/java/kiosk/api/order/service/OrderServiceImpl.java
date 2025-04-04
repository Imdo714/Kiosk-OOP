package kiosk.api.order.service;

import kiosk.api.menu.domain.MenuEntity;
import kiosk.api.menu.domain.MenuRepository;
import kiosk.api.menu.exception.MenuNotFoundException;
import kiosk.api.order.domain.OrderDetailEntity;
import kiosk.api.order.domain.OrderDetailRepository;
import kiosk.api.order.domain.OrderEntity;
import kiosk.api.order.domain.OrderRepository;
import kiosk.api.order.domain.request.OrderCreateRequest;
import kiosk.api.order.domain.request.OrderDetailRequest;
import kiosk.api.order.domain.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public OrderResponse createOrder(OrderCreateRequest request) {
        OrderEntity order = orderFactory.create(request);

        orderRepository.save(order);
        orderDetailRepository.saveAll(order.getOrderDetails());

        return OrderResponse.of(order, order.getOrderDetails());
    }
}
