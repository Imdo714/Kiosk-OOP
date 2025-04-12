package kiosk.api.order.repository;

import kiosk.api.menu.domain.common.MenuCategory;
import kiosk.api.menu.domain.common.MenuStatus;
import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.menu.repository.MenuRepository;
import kiosk.api.order.domain.dto.request.OrderCreateRequest;
import kiosk.api.order.domain.dto.request.OrderDetailRequest;
import kiosk.api.order.domain.internal.OrderDTO;
import kiosk.api.order.service.OrderServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static kiosk.api.menu.domain.common.MenuCategory.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@ActiveProfiles("test")
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @AfterEach
    void tearDown() {
        orderDetailRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        menuRepository.deleteAllInBatch();
    }

    @DisplayName("원하는 날 총 주문 수량 총 금액 조회")
    @Test
    void findDailyOrder() {
        // given
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();

        MenuEntity menu1 = createMenu("아메리카노", 1000, HANDMADE, MenuStatus.SELLING);
        menuRepository.save(menu1);

        OrderDetailRequest detailRequest = getOrderDetailRequest(menu1.getMenuId(), 2);
        List<OrderDetailRequest> details = List.of(detailRequest);

        OrderCreateRequest createRequest = OrderCreateRequest.builder()
                .orderDetails(details)
                .build();

        orderServiceImpl.createOrder(createRequest); // 주문 생성

        // when
        List<OrderDTO> dailyOrder = orderRepository.findDailyOrder(start, end);

        // then
        assertThat(dailyOrder).hasSize(1);
        assertThat(dailyOrder).extracting("orderPrice", "orderQuantity")
                .containsExactly(tuple(2000, 2));

    }

    private static OrderDetailRequest getOrderDetailRequest(Long menuId, int quantity) {
        return OrderDetailRequest.builder()
                .menuId(menuId)
                .orderQuantity(quantity)
                .build();
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