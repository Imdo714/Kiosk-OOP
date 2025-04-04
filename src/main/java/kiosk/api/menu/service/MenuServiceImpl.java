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
public class MenuServiceImpl implements MenuService{

    private final MenuRepository menuRepository;

    @Override
    public MenuResponse createMenu(MenuCreateRequest request) {
        MenuEntity menu = request.toEntity();
        MenuEntity savedMenu = menuRepository.save(menu);

        return MenuResponse.of(savedMenu);
    }

    @Override
    @Transactional
    public MenuResponse updateMenu(Long menuId, MenuUpdate request) {
        MenuEntity menu = findById(menuId);
        request.applyTo(menu);

        return MenuResponse.of(menu);
    }

    @Override
    public MenuEntity findById(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException("메뉴를 찾을 수 없습니다."));
    }


}
