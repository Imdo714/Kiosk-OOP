package kiosk.api.order.domain;

import kiosk.global.exception.handleException.InvalidEntityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderEntityTest {

    @DisplayName("주문 가격 수정 성공")
    @Test
    void updateOrderPrice() {
        // given
        OrderEntity order = new OrderEntity();

        // when
        order.updateOrderPrice(5000);

        // then
        assertEquals(5000, order.getOrderPrice());
    }

    @DisplayName("주문 가격을 업데이트 할때 가격은 0보다 커야 합니다.")
    @Test
    void updateOrderPriceWithException() {
        // given
        OrderEntity order = new OrderEntity();

        // when // then
        assertThrows(InvalidEntityException.class, () -> {
            order.updateOrderPrice(0);}, "가격은 0보다 커야 합니다.");
    }

    @DisplayName("수량을 업데이트 할때 0보다 커야 합니다.")
    @Test
    void updateOrderQuantityWithException() {
        // given
        OrderEntity order = new OrderEntity();

        // when // then
        assertThrows(InvalidEntityException.class, () -> {
            order.updateOrderPrice(0);}, "수량은 0보다 커야 합니다.");
    }

}