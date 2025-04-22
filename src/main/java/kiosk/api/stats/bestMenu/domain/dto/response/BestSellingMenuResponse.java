package kiosk.api.stats.bestMenu.domain.dto.response;

import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BestSellingMenuResponse {

    Long menuId;
    String menuName;
    Long totalQuantity;
}
