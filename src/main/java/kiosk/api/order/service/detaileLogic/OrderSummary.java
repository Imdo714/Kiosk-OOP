package kiosk.api.order.service.detaileLogic;

import kiosk.api.order.domain.request.OrderDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderSummary {

    private final int totalQuantity;
    private final int totalPrice;

    private OrderSummary(int totalQuantity, int totalPrice) {
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public static OrderSummary calculateTotal(List<OrderDTO> orders) {
        int totalQty = orders.stream().mapToInt(OrderDTO::getOrderQuantity).sum();
        int totalPrice = orders.stream().mapToInt(OrderDTO::getOrderPrice).sum();
        return new OrderSummary(totalQty, totalPrice);
    }

}
