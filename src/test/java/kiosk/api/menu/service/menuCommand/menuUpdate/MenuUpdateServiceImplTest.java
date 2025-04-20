package kiosk.api.menu.service.menuCommand.menuUpdate;

import kiosk.api.menu.domain.common.MenuCategory;
import kiosk.api.menu.domain.common.MenuStatus;
import kiosk.api.menu.domain.dto.request.MenuUpdate;
import kiosk.api.menu.domain.dto.response.MenuResponse;
import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.menu.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static kiosk.api.menu.domain.common.MenuCategory.HANDMADE;
import static kiosk.api.menu.domain.common.MenuStatus.SELLING;
import static kiosk.api.menu.domain.common.MenuStatus.STOP_SELLING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class MenuUpdateServiceImplTest {

    @Autowired
    private MenuUpdateService menuUpdateService;
    @Autowired
    private MenuRepository menuRepository;

    @DisplayName("메뉴 가격 상태값 수정 결과 값")
    @Test
    void updateMenu() {
        // given
        MenuEntity menu = createMenu("아메리카노", 1000, HANDMADE, SELLING);
        menuRepository.save(menu);

        MenuUpdate menuUpdate = MenuUpdate.builder()
                .menuPrice(2000)
                .menuStatus(STOP_SELLING)
                .build();

        // when
        MenuResponse menuResponse = menuUpdateService.updateMenu(menu.getMenuId(), menuUpdate);

        // then
        assertThat(menu)
                .extracting("menuPrice", "menuStatus")
                .containsExactlyInAnyOrder(2000, STOP_SELLING);
    }

    public MenuEntity createMenu(String menuName, int menuPrice, MenuCategory menuCategory, MenuStatus menuStatus) {
        return MenuEntity.builder()
                .menuName(menuName)
                .menuPrice(menuPrice)
                .menuCategory(menuCategory)
                .menuStatus(menuStatus)
                .build();
    }

}