package kiosk.api.menu.domain;

import kiosk.global.exception.handleException.InvalidEntityException;
import kiosk.global.exception.handleException.validEnumTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kiosk.api.menu.domain.MenuCategory.HANDMADE;
import static kiosk.api.menu.domain.MenuStatus.SELLING;
import static kiosk.api.menu.domain.MenuStatus.STOP_SELLING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuEntityTest {

    @DisplayName("메뉴 가격 수정 성공")
    @Test
    void updateMenuPrice() {
        // given
        MenuEntity menuEntity = new MenuEntity();

        // when
        menuEntity.updateMenuPrice(1500);

        // then
        assertEquals(1500, menuEntity.getMenuPrice());
    }

    @DisplayName("가격을 업데이트 할때 가격은 0이상 이거나 필수입니다.")
    @Test
    void updateMenuPriceWithException() {
        // given
        MenuEntity menuEntity = new MenuEntity();
        // when // then
        assertThrows(InvalidEntityException.class, () -> {
            menuEntity.updateMenuPrice(0);}, "가격은 0이상 이거나 필수입니다.");
    }

    @DisplayName("메뉴 상태 수정 성공")
    @Test
    void updateMenuStatus() {
        // given
        MenuEntity menuEntity = new MenuEntity();

        // when
        MenuStatus newStatus = MenuStatus.SELLING;  // 상태값을 판매중으로 설정
        menuEntity.updateMenuStatus(newStatus);

        // then
        assertEquals(newStatus, menuEntity.getMenuStatus());  // 기대값은 SELLING
    }

    @DisplayName("MenuStatus가 판매 중지일 때 예외")
    @Test
    void getOrderValid() {
        // given
        MenuEntity menu = MenuEntity.builder()
                .menuName("아메리카노")
                .menuPrice(1000)
                .menuCategory(HANDMADE)
                .menuStatus(STOP_SELLING)
                .build();

        // when // then
        assertThatThrownBy(() -> menu.getOrderValid()).isInstanceOf(validEnumTypeException.class).hasMessage("아메리카노는 판매 중지 상품입니다.");
    }

}