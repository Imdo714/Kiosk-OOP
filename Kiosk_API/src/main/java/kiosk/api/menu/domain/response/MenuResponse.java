package kiosk.api.menu.domain.response;

import kiosk.api.menu.domain.MenuCategory;
import kiosk.api.menu.domain.MenuEntity;
import kiosk.api.menu.domain.MenuStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MenuResponse {

    private Long menuId;

    private String menuName;

    private int menuPrice;

    private MenuCategory menuCategory;

    private MenuStatus menuStatus;

    public static MenuResponse of(MenuEntity meun){
        return MenuResponse.builder()
                .menuId(meun.getMenuId())
                .menuName(meun.getMenuName())
                .menuPrice(meun.getMenuPrice())
                .menuCategory(meun.getMenuCategory())
                .menuStatus(meun.getMenuStatus())
                .build();
    }
}
