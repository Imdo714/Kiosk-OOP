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
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public void createOrder(OrderCreateRequest request) {
        OrderEntity order = initializeOrder();

        int totalPrice = 0;
        int totalQuantity = 0;
        List<OrderDetailEntity> orderDetails = new ArrayList<>();

        for (OrderDetailRequest detail : request.getOrderDetails()) {

            MenuEntity menu = menuRepository.findById(detail.getMenuId())
                    .orElseThrow(() -> new MenuNotFoundException("해당 메뉴가 존재하지 않습니다."));

            int price = menu.getMenuPrice() * detail.getOrderQuantity();
            totalPrice += price;
            totalQuantity += detail.getOrderQuantity();

            OrderDetailEntity orderDetail = OrderDetailEntity.builder()
                    .orderEntity(order)  // (2) OrderEntity 설정
                    .menuEntity(menu)
                    .orderDetailQuantity(detail.getOrderQuantity())
                    .build();

            orderDetails.add(orderDetail);
        }

        orderDetailRepository.saveAll(orderDetails);
        log.info("orderDetails = {}", orderDetails);

        updateOrderPriceIfExists(order, totalPrice);
        updateOrderQuantityExists(order, totalQuantity);
        orderRepository.save(order);
    }

    private OrderEntity initializeOrder() {
        OrderEntity order = OrderEntity.builder()
                .orderPrice(0)
                .orderQuantity(0)
                .orderDate(LocalDateTime.now())
                .build();

        return orderRepository.save(order);
    }

    private void updateOrderQuantityExists(OrderEntity order, int totalQuantity) {
        order.updateOrderQuantity(totalQuantity);
    }

    private void updateOrderPriceIfExists(OrderEntity order, int totalPrice) {
        order.updateOrderPrice(totalPrice);
    }

}
