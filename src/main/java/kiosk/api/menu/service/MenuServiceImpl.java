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
    public MenuEntity findById(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException("메뉴를 찾을 수 없습니다."));
    }

    @Override
    public MenuEntity findByIdWithDiscount(Long menuId) {
        return menuRepository.findByIdWithDiscount(menuId)
                .orElseThrow(() -> new MenuNotFoundException("메뉴를 찾을 수 없습니다."));
    }


}
