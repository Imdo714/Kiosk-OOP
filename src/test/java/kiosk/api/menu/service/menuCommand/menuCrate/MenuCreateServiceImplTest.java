package kiosk.api.menu.service.menuCommand.menuCrate;

import kiosk.api.menu.domain.dto.request.MenuCreateRequest;
import kiosk.api.menu.domain.dto.response.MenuResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static kiosk.api.menu.domain.common.MenuCategory.HANDMADE;
import static kiosk.api.menu.domain.common.MenuStatus.SELLING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class MenuCreateServiceImplTest {

    @Autowired
    private MenuCreateService menuCreateService;

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
        MenuResponse menu = menuCreateService.createMenu(request);

        // then
        assertThat(menu)
                .extracting("menuName", "menuPrice", "menuCategory", "menuStatus")
                .containsExactlyInAnyOrder("아메리카노", 1000, HANDMADE, SELLING);
    }

}