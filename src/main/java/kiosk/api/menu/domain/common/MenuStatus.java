package kiosk.api.menu.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MenuStatus {

    SELLING("판매중"),
    STOP_SELLING("판매중지")
    ;

    private final String text;
}
