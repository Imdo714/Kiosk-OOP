package kiosk.api.order.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
public class OrderDetailRequest {

    private Long menuId; // 메뉴 ID
    private Integer orderQuantity; // 수량

}
