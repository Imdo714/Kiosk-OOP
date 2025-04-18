package kiosk.api.menu.service.menuCommand.menuUpdate;

import kiosk.api.menu.domain.dto.request.MenuUpdate;
import kiosk.api.menu.domain.dto.response.MenuResponse;

public interface MenuUpdateService {

    // 메뉴 수정
    MenuResponse updateMenu(Long menuId, MenuUpdate request);
}
