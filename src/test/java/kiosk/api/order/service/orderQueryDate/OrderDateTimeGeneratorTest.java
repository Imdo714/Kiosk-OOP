package kiosk.api.order.service.orderQueryDate;

import kiosk.api.menu.domain.common.MenuCategory;
import kiosk.api.menu.domain.common.MenuStatus;
import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.menu.repository.MenuRepository;
import kiosk.api.order.domain.dto.request.OrderCreateRequest;
import kiosk.api.order.domain.dto.request.OrderDetailRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.domain.dto.response.OrderResponse;
import kiosk.api.order.repository.OrderDetailRepository;
import kiosk.api.order.repository.OrderRepository;
import kiosk.api.order.service.OrderServiceImpl;
import kiosk.api.order.service.orderQueryDate.daily.DailyOrderQueryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static kiosk.api.menu.domain.common.MenuCategory.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class OrderDateTimeGeneratorTest {

    @Autowired
    private OrderServiceImpl orderServiceImpl;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderDateTimeGenerator orderDateTimeGenerator;

    @AfterEach
    void tearDown() {
        orderDetailRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        menuRepository.deleteAllInBatch();
    }

    @DisplayName("주문 기간 조회해서 총 주문 수, 총 금액 조회")
    @Test
    void getOrderDailyResponse() {
        // given
        createDummyOrder();

        LocalDate date = LocalDate.now();

        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.plusDays(1).atStartOfDay();

        // when
        OrderDateTotalResponse orderDailyResponse = orderDateTimeGenerator.getOrderDailyResponse(startDate, endDate, date);

        // then
        assertThat(orderDailyResponse).isNotNull();
        assertThat(orderDailyResponse.getTotalQuantity()).isEqualTo(3);
        assertThat(orderDailyResponse.getTotalPrice()).isEqualTo(3500);

    }

    public void createDummyOrder(){
        MenuEntity menu1 = createMenu("아메리카노", 1000, HANDMADE, MenuStatus.SELLING);
        MenuEntity menu2 = createMenu("카푸치노", 1500, HANDMADE, MenuStatus.SELLING);
        menuRepository.saveAll(List.of(menu1, menu2));

        OrderDetailRequest orderDetailRequest1 = getOrderDetailRequest(menu1.getMenuId(), 2);
        OrderDetailRequest orderDetailRequest2 = getOrderDetailRequest(menu2.getMenuId(), 1);

        List<OrderDetailRequest> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetailRequest1);
        orderDetails.add(orderDetailRequest2);

        OrderCreateRequest request = OrderCreateRequest.builder()
                .orderDetails(orderDetails)
                .build();

        // when
        OrderResponse order = orderServiceImpl.createOrder(request);
    }

    private static OrderDetailRequest getOrderDetailRequest(Long menuId, int quantity) {
        return OrderDetailRequest.builder()
                .menuId(menuId)
                .orderQuantity(quantity)
                .build();
    }

    private MenuEntity createMenu(String menuName, int menuPrice, MenuCategory menuCategory, MenuStatus menuStatus) {
        return MenuEntity.builder()
                .menuName(menuName)
                .menuPrice(menuPrice)
                .menuCategory(menuCategory)
                .menuStatus(menuStatus)
                .build();
    }

}