package kiosk.api.menu.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum MenuCategory {

    HANDMADE("제조 음료"),
    BOTTLE("병 음료"),
    BAKERY("베이커리")
    ;

    private final String text;

}
