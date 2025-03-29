package kiosk.api.order.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Builder
@AllArgsConstructor
public class OrderCreateRequest {

    private List<OrderDetailRequest> orderDetails; // 주문 상세 정보 목록
}
