package kiosk.api.menu.service.menuQuery.menuListQuery;

import kiosk.api.menu.domain.dto.response.MenuListResponse;

public interface MenuListQuery {

    // 메뉴 리스트 조회 동적으로 조건 포함
    MenuListResponse listQueryMenu(String category, String name, String status);

}
