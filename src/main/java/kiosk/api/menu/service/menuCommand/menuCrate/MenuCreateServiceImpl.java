package kiosk.api.menu.service.menuCommand.menuCrate;

import kiosk.api.menu.domain.dto.request.MenuCreateRequest;
import kiosk.api.menu.domain.dto.response.MenuResponse;
import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MenuCreateServiceImpl implements MenuCreateService{

    private final MenuRepository menuRepository;

    @Override
    public MenuResponse createMenu(MenuCreateRequest request) {
        MenuEntity menu = request.toEntity();
        MenuEntity savedMenu = menuRepository.save(menu);

        return MenuResponse.of(savedMenu);
    }

}
