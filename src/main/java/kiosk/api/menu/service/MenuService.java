package kiosk.api.menu.service;

import kiosk.api.menu.domain.MenuEntity;
import kiosk.api.menu.domain.request.MenuCreateRequest;
import kiosk.api.menu.domain.request.MenuUpdate;
import kiosk.api.menu.domain.response.MenuResponse;

public interface MenuService {

    MenuResponse createMenu(MenuCreateRequest request);

    MenuResponse updateMenu(Long menuId, MenuUpdate request);

    MenuEntity findById(Long menuId);
}
