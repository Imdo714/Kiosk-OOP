package kiosk.api.order.domain.dto.request;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {

    private List<OrderDetailRequest> orderDetails; // 주문 상세 정보 목록
}
