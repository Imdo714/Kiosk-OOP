package kiosk.api.menu.domain.request;

import jakarta.validation.constraints.Positive;
import kiosk.api.menu.domain.MenuStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MenuUpdate {

    @Positive(message = "메뉴 가격은 양수여야 합니다.")
    private Integer menuPrice;

    private MenuStatus menuStatus;

    public boolean hasPrice() {
        return menuPrice != null;
    }

    public boolean hasStatus() {
        return menuStatus != null;
    }

}
