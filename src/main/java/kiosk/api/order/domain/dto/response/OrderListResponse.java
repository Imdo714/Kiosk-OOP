package kiosk.api.order.domain.dto.response;

import kiosk.api.order.domain.dto.request.OrderDTO;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderListResponse {

    private List<OrderInfo> orderList;
    private Pageable pageInfo;

    @Getter
    @Builder
    public static class OrderInfo {
        private long orderId;
        private int orderPrice;
        private int orderQuantity;
        private LocalDateTime orderDate;
    }

    @Getter
    @Builder
    public static class Pageable {
        private final long totalElements;
        private final int totalPages;
        private final int currentPage;
        private final int size;
    }

    public static OrderListResponse of(Page<OrderDTO> res){
        return OrderListResponse.builder()
                .orderList(res.stream()
                        .map(o -> OrderInfo.builder()
                                .orderId(o.getOrderId())
                                .orderPrice(o.getOrderPrice())
                                .orderQuantity(o.getOrderQuantity())
                                .orderDate(o.getOrderDate())
                                .build()
                        ).collect(Collectors.toList())
                ).pageInfo(Pageable.builder()
                        .totalElements(res.getTotalElements())
                        .totalPages(res.getTotalPages())
                        .currentPage(res.getNumber())
                        .size(res.getSize())
                        .build()
                )
                .build();
    }

}
