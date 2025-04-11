package kiosk.api.menu.service;

import kiosk.api.menu.domain.common.MenuCategory;
import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.menu.domain.dto.response.MenuListResponse;
import kiosk.api.menu.repository.MenuRepository;
import kiosk.api.menu.domain.common.MenuStatus;
import kiosk.api.menu.domain.dto.request.MenuCreateRequest;
import kiosk.api.menu.domain.dto.request.MenuUpdate;
import kiosk.api.menu.domain.dto.response.MenuResponse;
import kiosk.global.exception.handleException.validEnumTypeException;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kiosk.api.menu.domain.common.MenuCategory.BOTTLE;
import static kiosk.api.menu.domain.common.MenuCategory.HANDMADE;
import static kiosk.api.menu.domain.common.MenuStatus.SELLING;
import static kiosk.api.menu.domain.common.MenuStatus.STOP_SELLING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        MenuResponse menuResponse = menuServiceImpl.updateMenu(menu.getMenuId(), menuUpdate);

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

    @DisplayName("전체 메뉴 조회")
    @Test
    void selectMenu() {
        // given
        createMenuInitial();

        // when
        MenuListResponse dslAll = menuServiceImpl.selectMenu(null, null, null);

        // then
        assertThat(dslAll.getMenuEntityList()).hasSize(2)
                .extracting("menuName", "menuPrice", "menuCategory", "menuStatus")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("아메리카노", 1000, HANDMADE, SELLING),
                        Tuple.tuple("카푸치노", 1500, BOTTLE, SELLING)
                );
    }

    @DisplayName("전체 메뉴 조회시 HANDMADE 카테고리 조건")
    @Test
    void selectMenuWhitCategory() {
        // given
        createMenuInitial();

        // when
        MenuListResponse dslAll = menuServiceImpl.selectMenu("HANDMADE", null, null);

        // then
        assertThat(dslAll.getMenuEntityList()).hasSize(1)
                .extracting("menuName", "menuPrice", "menuCategory", "menuStatus")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("아메리카노", 1000, HANDMADE, SELLING)
                );
    }

    @DisplayName("전체 메뉴 조회시 이름 검색 조건")
    @Test
    void selectMenuWhitName() {
        // given
        createMenuInitial();

        // when
        MenuListResponse dslAll = menuServiceImpl.selectMenu(null, "아메리카노", null);

        // then
        assertThat(dslAll.getMenuEntityList()).hasSize(1)
                .extracting("menuName", "menuPrice", "menuCategory", "menuStatus")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("아메리카노", 1000, HANDMADE, SELLING)
                );
    }

    @DisplayName("전체 메뉴 조회시 SELLING 상태값 조건")
    @Test
    void selectMenuWhitMenuStatus() {
        // given
        createMenuInitial();

        // when
        MenuListResponse dslAll = menuServiceImpl.selectMenu(null, null, "SELLING");

        // then
        assertThat(dslAll.getMenuEntityList()).hasSize(2)
                .extracting("menuName", "menuPrice", "menuCategory", "menuStatus")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("아메리카노", 1000, HANDMADE, SELLING),
                        Tuple.tuple("카푸치노", 1500, BOTTLE, SELLING)
                );
    }

    @DisplayName("전체 메뉴 조회시 없는 카테고리 조건시 예외")
    @Test
    void selectMenuWhitNotCategory() {
        // given
        createMenuInitial();

        // when // then
        assertThatThrownBy(() -> menuServiceImpl.selectMenu("NOT_CATEGORY", null, null))
                .isInstanceOf(validEnumTypeException.class)
                .hasMessage("존재하지 않는 카테고리입니다.");
    }

    @DisplayName("전체 메뉴 조회시 없는 상태값 조건시 예외")
    @Test
    void selectMenuWhitNotMenuStatus() {
        // given
        createMenuInitial();

        // when // then
        assertThatThrownBy(() -> menuServiceImpl.selectMenu(null, null, "NOT_STATUS"))
                .isInstanceOf(validEnumTypeException.class)
                .hasMessage("존재하지 않는 상태 값입니다.");
    }

    private void createMenuInitial() {
        MenuEntity menu1 = createMenu("아메리카노", 1000, HANDMADE, SELLING);
        MenuEntity menu2 = createMenu("카푸치노", 1500, BOTTLE, SELLING);
        menuRepository.saveAll(List.of(menu1, menu2));
    }

}