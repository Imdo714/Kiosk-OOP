package kiosk.api.menu.service;

import kiosk.api.menu.domain.MenuEntity;
import kiosk.api.menu.domain.MenuRepository;
import kiosk.api.menu.domain.request.MenuCreateRequest;
import kiosk.api.menu.domain.request.MenuUpdate;
import kiosk.api.menu.domain.response.MenuResponse;
import kiosk.api.menu.exception.MenuNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuResponse createMenu(MenuCreateRequest request) {
        MenuEntity menu = request.toEntity();
        MenuEntity savedMenu = menuRepository.save(menu);

        return MenuResponse.of(savedMenu);
    }

    @Transactional
    public MenuResponse updateMenu(Long menuId, MenuUpdate request) {
        MenuEntity menu = getMenuEntity(menuId);

        updatePriceIfExists(request, menu);
        updateMenuStatusIfExists(request, menu);

        return MenuResponse.of(menu);
    }

    private void updateMenuStatusIfExists(MenuUpdate request, MenuEntity menu) {
        if (request.hasStatus()) {
            menu.updateMenuStatus(request.getMenuStatus());
        }
    }

    private void updatePriceIfExists(MenuUpdate request, MenuEntity menu) {
        if (request.hasPrice()) {
            menu.updateMenuPrice(request.getMenuPrice());
        }
    }

    private MenuEntity getMenuEntity(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException("메뉴를 찾을 수 없습니다."));
    }

}
