package kiosk.api.order.service;

import kiosk.api.order.repository.OrderDetailRepository;
import kiosk.api.order.domain.entity.OrderEntity;
import kiosk.api.order.repository.OrderRepository;
import kiosk.api.order.domain.dto.request.OrderCreateRequest;
import kiosk.api.order.domain.dto.response.OrderResponse;
import kiosk.api.order.service.orderGetDate.OrderDateTimeGenerator;
import kiosk.api.order.service.detaileLogic.OrderFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderFactory orderFactory;
    private final OrderDateTimeGenerator orderDateTimeGenerator;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {
        OrderEntity order = orderFactory.create(request);

        orderRepository.save(order);
        orderDetailRepository.saveAll(order.getOrderDetails());

        return OrderResponse.of(order, order.getOrderDetails());
    }


}
