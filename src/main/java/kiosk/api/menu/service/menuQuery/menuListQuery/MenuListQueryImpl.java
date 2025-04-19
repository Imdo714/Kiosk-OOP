package kiosk.api.menu.service.menuQuery.menuListQuery;

import kiosk.api.menu.domain.dto.response.MenuListResponse;
import kiosk.api.menu.domain.dto.response.MenuResponse;
import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuListQueryImpl implements MenuListQuery{

    private final MenuRepository menuRepository;

    @Override
    public MenuListResponse listQueryMenu(String category, String name, String status) {
        List<MenuResponse> dslAll = menuRepository.selectMenu(category, name, status);

        return MenuListResponse.arr(dslAll);
    }
}
