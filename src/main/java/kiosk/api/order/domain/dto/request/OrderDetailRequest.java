package kiosk.api.order.domain.dto.request;

import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailRequest {

    private Long menuId; // 메뉴 ID
    private Integer orderQuantity; // 수량

}
