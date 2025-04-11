package kiosk.api.order.domain.dto.response;

import kiosk.api.order.domain.entity.OrderDetailEntity;
import kiosk.api.order.domain.entity.OrderEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@Builder
public class OrderResponse {

    private Long orderId;
    private int orderPrice;
    private int orderQuantity;
    private LocalDateTime orderDate;
    private List<OrderDetailResult> orderResultsList;

    @Getter
    @Builder
    public static class OrderDetailResult{
        private Long orderDetailId;
        private int orderDetailQuantity;
        private String orderDetailMenuName;
        private int orderDetailMenuPrice;
    }

    public static OrderResponse of(OrderEntity order, List<OrderDetailEntity> orderDetail){
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .orderPrice(order.getOrderPrice())
                .orderQuantity(order.getOrderQuantity())
                .orderDate(order.getOrderDate())
                .orderResultsList(orderDetail.stream()
                        .map(detail -> OrderDetailResult.builder()
                                .orderDetailId(detail.getOrderDetailId())
                                .orderDetailQuantity(detail.getOrderDetailQuantity())
                                .orderDetailMenuName(detail.getMenuEntity().getMenuName())
                                .orderDetailMenuPrice(detail.getMenuEntity().getMenuPrice())
                                .build())
                                .collect(Collectors.toList())
                        )
                .build();
    }
}
