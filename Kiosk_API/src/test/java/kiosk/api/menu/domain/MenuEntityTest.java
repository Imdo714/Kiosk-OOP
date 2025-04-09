package kiosk.api.menu.domain;

import kiosk.global.exception.handleException.InvalidEntityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

}