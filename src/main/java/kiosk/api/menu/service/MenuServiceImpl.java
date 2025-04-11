package kiosk.api.menu.service;

import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.menu.domain.dto.response.MenuListResponse;
import kiosk.api.menu.repository.MenuRepository;
import kiosk.api.menu.domain.dto.request.MenuCreateRequest;
import kiosk.api.menu.domain.dto.request.MenuUpdate;
import kiosk.api.menu.domain.dto.response.MenuResponse;
import kiosk.global.exception.handleException.MenuNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public MenuListResponse selectMenu(String category, String name, String status) {
        List<MenuEntity> dslAll = menuRepository.selectMenu(category, name, status);

        return MenuListResponse.arr(dslAll);
    }


}
