package kiosk.api.order.service;

import kiosk.api.menu.domain.MenuCategory;
import kiosk.api.menu.domain.MenuEntity;
import kiosk.api.menu.domain.MenuRepository;
import kiosk.api.menu.domain.MenuStatus;
import kiosk.api.order.domain.OrderEntity;
import kiosk.api.order.domain.OrderRepository;
import kiosk.api.order.domain.request.OrderCreateRequest;
import kiosk.api.order.domain.request.OrderDetailRequest;
import kiosk.api.order.domain.response.OrderResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static kiosk.api.menu.domain.MenuCategory.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MenuRepository menuRepository;

//    @BeforeEach
//    void setUp() {
//        orderRepository.deleteAll();
//        menuRepository.deleteAll();
//    }

    /**
     * @Transactional 을 사용하면 각 메서드가 끝난 후 롤백 됨
     * @BeforeEach 을 사용하면 각 메서드가 끝난 후 DB 초기화 됨
     *
     * 현재 문제 : 테스트를 각각 실행하면 성공하지만 한번에 실행하면 DB가 롤백되지 않는 현상이 생김
     *
     * @Transactional를 사용해서 DB를 초기화시키면 해당 메뉴가 존재하지 않습니다. 라는 예외가 터짐 즉 MenuId가 없음
     * @BeforeEach를 사용해서 DB를 초기화시키면 제약 조건 위반 메뉴를 삭제 할 수 없다고 함
     */

    @DisplayName("단일 주문 생성 성공")
    @Test
    void createOrder() {
        // given
        MenuEntity menu1 = createMenu("아메리카노", 1000, HANDMADE, MenuStatus.SELLING);
        menuRepository.save(menu1);

        OrderDetailRequest orderDetailRequest1 = getOrderDetailRequest(1L, 2);

        List<OrderDetailRequest> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetailRequest1);

        OrderCreateRequest request = OrderCreateRequest.builder()
                .orderDetails(orderDetails)
                .build();

        // when
        OrderResponse order = orderService.createOrder(request);

        // then
        assertThat(order).isNotNull();
        assertThat(order.getOrderId()).isNotNull();
        assertThat(order.getOrderPrice()).isEqualTo(2000);
        assertThat(order.getOrderQuantity()).isEqualTo(2);
        assertThat(order.getOrderResultsList()).hasSize(1);

        OrderResponse.OrderDetailResult firstDetail = order.getOrderResultsList().get(0);
        assertThat(firstDetail.getOrderDetailId()).isNotNull();
        assertThat(firstDetail.getOrderDetailQuantity()).isEqualTo(2);
        assertThat(firstDetail.getOrderDetailMenuPrice()).isEqualTo(1000);
        assertThat(firstDetail.getOrderDetailMenuName()).isEqualTo("아메리카노");
    }

    @DisplayName("2개 이상 주문 생성 성공")
    @Test
    void createOrderList() {
        // given
        MenuEntity menu1 = createMenu("아메리카노", 1000, HANDMADE, MenuStatus.SELLING);
        MenuEntity menu2 = createMenu("카푸치노", 1500, HANDMADE, MenuStatus.SELLING);
        menuRepository.saveAll(List.of(menu1, menu2));

        OrderDetailRequest orderDetailRequest1 = getOrderDetailRequest(1L, 2);
        OrderDetailRequest orderDetailRequest2 = getOrderDetailRequest(2L, 1);

        List<OrderDetailRequest> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetailRequest1);
        orderDetails.add(orderDetailRequest2);

        OrderCreateRequest request = OrderCreateRequest.builder()
                .orderDetails(orderDetails)
                .build();

        // when
        OrderResponse order = orderService.createOrder(request);

        // then
        assertThat(order).isNotNull();
        assertThat(order.getOrderId()).isNotNull();
        assertThat(order.getOrderPrice()).isEqualTo(3500);
        assertThat(order.getOrderQuantity()).isEqualTo(3);
        assertThat(order.getOrderResultsList()).hasSize(2);

        OrderResponse.OrderDetailResult firstDetail = order.getOrderResultsList().get(0);
        assertThat(firstDetail.getOrderDetailId()).isNotNull();
        assertThat(firstDetail.getOrderDetailQuantity()).isEqualTo(2);
        assertThat(firstDetail.getOrderDetailMenuPrice()).isEqualTo(1000);
        assertThat(firstDetail.getOrderDetailMenuName()).isEqualTo("아메리카노");

        OrderResponse.OrderDetailResult secondDetail = order.getOrderResultsList().get(1);
        assertThat(secondDetail.getOrderDetailId()).isNotNull();
        assertThat(secondDetail.getOrderDetailQuantity()).isEqualTo(1);
        assertThat(secondDetail.getOrderDetailMenuPrice()).isEqualTo(1500);
        assertThat(secondDetail.getOrderDetailMenuName()).isEqualTo("카푸치노");
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