package kiosk.api.order.service.detaileLogic;

import kiosk.api.discount.domain.entity.DiscountEntity;
import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.menu.service.MenuService;
import kiosk.api.order.domain.entity.OrderEntity;
import kiosk.api.order.domain.dto.request.OrderCreateRequest;
import kiosk.api.order.domain.dto.request.OrderDetailRequest;
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
            MenuEntity menu = menuService.findByIdWithDiscount(detail.getMenuId());

            menu.getOrderValid();

            order.addOrderDetail(menu, detail.getOrderQuantity());
        }

        return order;
    }


}
