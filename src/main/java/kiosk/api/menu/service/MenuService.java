package kiosk.api.menu.service;

import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.menu.domain.dto.request.MenuCreateRequest;
import kiosk.api.menu.domain.dto.request.MenuUpdate;
import kiosk.api.menu.domain.dto.response.MenuListResponse;
import kiosk.api.menu.domain.dto.response.MenuResponse;

public interface MenuService {

    MenuResponse createMenu(MenuCreateRequest request);

    MenuResponse updateMenu(Long menuId, MenuUpdate request);

    MenuEntity findById(Long menuId);

    MenuListResponse selectMenu(String category, String name, String status);
}
