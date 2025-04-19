package kiosk.api.order.domain.dto.response;

import kiosk.api.discount.domain.entity.DiscountEntity;
import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.order.domain.entity.OrderDetailEntity;
import kiosk.api.order.domain.entity.OrderEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
        private Long discountId;
        private int orderDetailMenuPrice;
        private int sum;
    }

    public static OrderResponse of(OrderEntity order, List<OrderDetailEntity> orderDetails) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .orderPrice(order.getOrderPrice())
                .orderQuantity(order.getOrderQuantity())
                .orderDate(order.getOrderDate())
                .orderResultsList(orderDetails.stream()
                        .map(detail -> {
                            MenuEntity menu = detail.getMenuEntity();
                            Optional<DiscountEntity> discountOpt = menu.getActiveDiscount();

                            return OrderDetailResult.builder()
                                    .orderDetailId(detail.getOrderDetailId())
                                    .orderDetailMenuName(menu.getMenuName())
                                    .discountId(
                                            discountOpt
                                                    .map(DiscountEntity::getDiscountId)
                                                        .orElse(null)
                                    )
                                    .orderDetailQuantity(detail.getOrderDetailQuantity())
                                    .orderDetailMenuPrice(detail.getOrderDetailPrice())
                                    .sum(detail.getOrderDetailPrice() * detail.getOrderDetailQuantity())
                                    .build();
                        })
                        .collect(Collectors.toList()))
                .build();
    }

//    public static OrderResponse of(OrderEntity order, List<OrderDetailEntity> orderDetail){
//
//        return OrderResponse.builder()
//                .orderId(order.getOrderId())
//                .orderPrice(order.getOrderPrice())
//                .orderQuantity(order.getOrderQuantity())
//                .orderDate(order.getOrderDate())
//                .orderResultsList(orderDetail.stream()
//                        .map(detail -> OrderDetailResult.builder()
//                                .orderDetailId(detail.getOrderDetailId())
//                                .orderDetailMenuName(detail.getMenuEntity().getMenuName())
//
//                                .discountName(detail.getMenuEntity().getDiscountEntity())
//
//                                .orderDetailQuantity(detail.getOrderDetailQuantity())
//                                .orderDetailMenuPrice(detail.getOrderDetailPrice())
//                                .sum(detail.getOrderDetailPrice() * detail.getOrderDetailQuantity())
//                                .build())
//                                .collect(Collectors.toList())
//                        )
//                .build();
//    }
}
