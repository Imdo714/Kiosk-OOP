package kiosk.api.order.service;

import kiosk.api.menu.domain.MenuEntity;
import kiosk.api.menu.service.MenuService;
import kiosk.api.order.domain.OrderEntity;
import kiosk.api.order.domain.request.OrderCreateRequest;
import kiosk.api.order.domain.request.OrderDetailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderFactory {

    private final MenuService menuService;

    public OrderEntity create(OrderCreateRequest request) {
        OrderEntity order = OrderEntity.createNow(0, 0);

        for (OrderDetailRequest detail : request.getOrderDetails()) {
            MenuEntity menu = menuService.findById(detail.getMenuId());
            menu.getOrderValid();

            order.addOrderDetail(menu, detail.getOrderQuantity());
        }

        return order;
    }
}
