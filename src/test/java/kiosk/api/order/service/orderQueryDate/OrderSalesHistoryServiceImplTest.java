package kiosk.api.order.service.orderQueryDate;

import kiosk.api.menu.domain.common.MenuCategory;
import kiosk.api.menu.domain.common.MenuStatus;
import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.menu.repository.MenuRepository;
import kiosk.api.order.domain.common.DateType;
import kiosk.api.order.domain.dto.request.OrderCreateRequest;
import kiosk.api.order.domain.dto.request.OrderDetailRequest;
import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.domain.dto.response.OrderListResponse;
import kiosk.api.order.service.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static kiosk.api.menu.domain.common.MenuCategory.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class OrderSalesHistoryServiceImplTest {

    @Autowired
    private OrderServiceImpl orderServiceImpl;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private OrderSalesHistoryService orderSalesHistoryService;

    @DisplayName("DateType이 DAILY일 때 총 주문 수량 총 금액 조회")
    @Test
    void getSalesReportByPeriodDaily() {
        // given
        OrderDummyTest();

        OrderDateRequest dateRequest = OrderDateRequest.builder()
                .date(LocalDate.now())
                .dateType(DateType.DAILY)
                .build();

        // when
        OrderDateTotalResponse dailyOrder = orderSalesHistoryService.getSalesReportByPeriod(dateRequest);

        // then
        assertThat(dailyOrder).isNotNull();
        assertThat(dailyOrder.getTotalPrice()).isEqualTo(2000);
        assertThat(dailyOrder.getTotalQuantity()).isEqualTo(2);
    }

    private void OrderDummyTest() {
        MenuEntity menu1 = createMenu("아메리카노", 1000, HANDMADE, MenuStatus.SELLING);
        menuRepository.save(menu1);

        OrderDetailRequest detailRequest = getOrderDetailRequest(menu1.getMenuId(), 2);
        List<OrderDetailRequest> details = List.of(detailRequest);

        OrderCreateRequest createRequest = OrderCreateRequest.builder()
                .orderDetails(details)
                .build();

        orderServiceImpl.createOrder(createRequest);
    }

    @DisplayName("")
    @Test
    void getOrderHistoryByPeriodDaily() {
        // given
        ListOrderDummyTest();

        OrderDateRequest dateRequest = OrderDateRequest.builder()
                .date(LocalDate.now())
                .dateType(DateType.DAILY)
                .build();
        Pageable pageable = PageRequest.of(0, 20);

        // when
        OrderListResponse historyByPeriod = orderSalesHistoryService.getOrderHistoryByPeriod(dateRequest, pageable);

        // then
        assertThat(historyByPeriod.getOrderList()).hasSize(2);
        assertThat(historyByPeriod.getOrderList().get(0).getOrderPrice()).isEqualTo(5000);
        assertThat(historyByPeriod.getOrderList().get(1).getOrderPrice()).isEqualTo(3000);
        assertThat(historyByPeriod.getPageInfo().getCurrentPage()).isEqualTo(0);
    }

    private void ListOrderDummyTest() {
        MenuEntity menu1 = createMenu("아메리카노", 1000, HANDMADE, MenuStatus.SELLING);
        MenuEntity menu2 = createMenu("아이스티", 1500, HANDMADE, MenuStatus.SELLING);
        menuRepository.saveAll(List.of(menu1, menu2));

        List<OrderDetailRequest> details1 = List.of(
                getOrderDetailRequest(menu1.getMenuId(), 2),
                getOrderDetailRequest(menu2.getMenuId(), 2)
        );
        OrderCreateRequest createRequest1 = OrderCreateRequest.builder()
                .orderDetails(details1)
                .build();
        orderServiceImpl.createOrder(createRequest1);

        List<OrderDetailRequest> details2 = List.of(
                getOrderDetailRequest(menu2.getMenuId(), 2)
        );
        OrderCreateRequest createRequest2 = OrderCreateRequest.builder()
                .orderDetails(details2)
                .build();
        orderServiceImpl.createOrder(createRequest2);
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