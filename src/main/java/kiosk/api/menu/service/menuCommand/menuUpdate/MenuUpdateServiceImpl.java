package kiosk.api.menu.service.menuCommand.menuUpdate;

import kiosk.api.menu.domain.dto.request.MenuUpdate;
import kiosk.api.menu.domain.dto.response.MenuResponse;
import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MenuUpdateServiceImpl implements MenuUpdateService{

    private final MenuService menuService;

    @Override
    @Transactional
    public MenuResponse updateMenu(Long menuId, MenuUpdate request) {
        MenuEntity menu = menuService.findById(menuId);
        request.applyTo(menu);

        return MenuResponse.of(menu);
    }

}
