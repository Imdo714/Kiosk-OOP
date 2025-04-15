package kiosk.api.discount.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscountType {

    PERCENT("할인 율"),
    FIXED("할인 금액")
    ;

    private final String text;
}
