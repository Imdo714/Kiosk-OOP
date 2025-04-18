package kiosk.api.menu.service.menuCommand.menuCrate;

import kiosk.api.menu.domain.dto.request.MenuCreateRequest;
import kiosk.api.menu.domain.dto.response.MenuResponse;

public interface MenuCreateService {

    // 메뉴 생성
    MenuResponse createMenu(MenuCreateRequest request);
}
