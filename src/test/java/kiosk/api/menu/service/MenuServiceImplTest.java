package kiosk.api.menu.service;

import kiosk.api.menu.domain.MenuCategory;
import kiosk.api.menu.domain.MenuEntity;
import kiosk.api.menu.repository.MenuRepository;
import kiosk.api.menu.domain.MenuStatus;
import kiosk.api.menu.domain.request.MenuCreateRequest;
import kiosk.api.menu.domain.request.MenuUpdate;
import kiosk.api.menu.domain.response.MenuResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static kiosk.api.menu.domain.MenuCategory.HANDMADE;
import static kiosk.api.menu.domain.MenuStatus.SELLING;
import static kiosk.api.menu.domain.MenuStatus.STOP_SELLING;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class MenuServiceImplTest {

    @Autowired
    private MenuServiceImpl menuServiceImpl;

    @Autowired
    private MenuRepository menuRepository;

    @DisplayName("메뉴 등록 결과 값")
    @Test
    void createMenu() {
        // given
        MenuCreateRequest request = MenuCreateRequest.builder()
                .menuName("아메리카노")
                .menuPrice(1000)
                .menuCategory(HANDMADE)
                .menuStatus(SELLING)
                .build();

        // when
        MenuResponse menu = menuServiceImpl.createMenu(request);

        // then
        assertThat(menu)
                .extracting("menuName", "menuPrice", "menuCategory", "menuStatus")
                .containsExactlyInAnyOrder("아메리카노", 1000, HANDMADE, SELLING);
    }

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
        MenuResponse menuResponse = menuServiceImpl.updateMenu(1L, menuUpdate);

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