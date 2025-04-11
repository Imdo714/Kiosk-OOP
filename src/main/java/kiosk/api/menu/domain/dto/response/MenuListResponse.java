package kiosk.api.menu.domain.dto.response;

import kiosk.api.menu.domain.entity.MenuEntity;
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
