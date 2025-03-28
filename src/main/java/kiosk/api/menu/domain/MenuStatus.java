package kiosk.api.menu.domain;

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
