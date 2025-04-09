package kiosk.api.menu.domain.response;

import kiosk.api.menu.domain.MenuCategory;
import kiosk.api.menu.domain.MenuEntity;
import kiosk.api.menu.domain.MenuStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@Builder
public class MenuListResponse {

    private List<MenuEntity> menuEntityList;

    public static MenuListResponse arr(List<MenuEntity> dslAll){
        return MenuListResponse.builder()
                .menuEntityList(dslAll.stream()
                    .map(menu -> MenuEntity.builder()
                        .menuId(menu.getMenuId())
                        .menuName(menu.getMenuName())
                        .menuPrice(menu.getMenuPrice())
                        .menuCategory(menu.getMenuCategory())
                        .menuStatus(menu.getMenuStatus())
                        .build()
                    )
                    .collect(Collectors.toList())
                )
                .build();
    }

}
