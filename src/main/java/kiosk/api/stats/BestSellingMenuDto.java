package kiosk.api.stats;

import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BestSellingMenuDto {

    Long menuId;
    String menuName;
    Long totalQuantity;
}
