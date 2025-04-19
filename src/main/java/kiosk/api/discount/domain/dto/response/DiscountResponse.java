package kiosk.api.discount.domain.dto.response;

import kiosk.api.discount.domain.common.DiscountType;
import kiosk.api.discount.domain.entity.DiscountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DiscountResponse {

    private Long discountId;
    private String discountCode;
    private DiscountType discountType;
    private int discountValue;
    private LocalDateTime discountStart;
    private LocalDateTime discountEnd;
    private Long menuId;

    public static DiscountResponse of(DiscountEntity discount){
        return DiscountResponse.builder()
                .discountId(discount.getDiscountId())
                .discountCode(discount.getDiscountCode())
                .discountType(discount.getDiscountType())
                .discountValue(discount.getDiscountValue())
                .discountStart(discount.getDiscountStart())
                .discountEnd(discount.getDiscountEnd())
                .menuId(discount.getMenuEntity().getMenuId())
                .build();
    }

}
