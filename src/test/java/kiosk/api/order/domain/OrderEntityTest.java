package kiosk.api.order.domain;

import kiosk.api.order.domain.entity.OrderEntity;
import kiosk.global.exception.handleException.InvalidEntityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertThatThrownBy(() -> order.updateOrderPrice(0))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("가격은 0보다 커야 합니다.");
    }


    @DisplayName("주문 수량은 0보다 커야 합니다.")
    @Test
    void checkQuantity() {
        // given
        OrderEntity order = new OrderEntity();

        // when // then
        assertThatThrownBy(() -> order.checkQuantity(0))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("주문 수량은 0보다 커야 합니다.");
    }

}