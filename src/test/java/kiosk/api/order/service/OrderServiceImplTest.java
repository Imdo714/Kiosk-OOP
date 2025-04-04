package kiosk.api.order.service;

import kiosk.api.menu.domain.MenuCategory;
import kiosk.api.menu.domain.MenuEntity;
import kiosk.api.menu.domain.MenuRepository;
import kiosk.api.menu.domain.MenuStatus;
import kiosk.api.menu.exception.MenuNotFoundException;
import kiosk.api.order.domain.request.OrderCreateRequest;
import kiosk.api.order.domain.request.OrderDetailRequest;
import kiosk.api.order.domain.response.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kiosk.api.menu.domain.MenuCategory.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderServiceImpl;
    @Autowired
    private MenuRepository menuRepository;

//    @BeforeEach
//    void setUp() {
//        menuRepository.deleteAll();
//    }

//    @AfterEach
//    void tearDown() {
//        menuRepository.deleteAllInBatch();
//    }

    /**
     * @Transactional 을 사용하면 각 메서드가 끝난 후 롤백 됨
     * @BeforeEach 을 사용하면 각 메서드가 끝난 후 DB 초기화 됨
     *
     * 현재 문제 : 테스트를 각각 실행하면 성공하지만 한번에 실행하면 없는 메뉴라고 나오거나 제약 조건 위반이라 나옴
     *
     * @Transactional를 사용해서 DB를 초기화시키면 해당 메뉴가 존재하지 않습니다. 라는 예외가 터짐 즉 MenuId가 없음
     * @BeforeEach를 사용해서 DB를 초기화시키면 제약 조건 위반 메뉴를 삭제 할 수 없다고 함
     *
     * Transactional는 최초에 조회해올 때 스냡샷을 뜨고 마지막에 Transactional 종료 시점에 인스턴스를 비교를 해서 달라진 부분이 있다면 업데이트 쿼리가 나감
     *
     * 첫 번째 테스트 실행시 엔티티 조회하면서 영속성 컨텍스트(1차 캐시)에 저장됨 트랙젹션이 끝나면서 변경된 부분이 있다면 업데이트 쿼리가 나감 테스트 성공
     * 두 번째 테스트 실행시 동일한 데이터를 다시 등록하려고 하면 기존 영속성 컨텍스트와 충돌이 발생 특히 트랜잭션이 종료될 때 스냅샷과 비교하면서 예상치 못한 업데이트 쿼리가 나감
     *
     * 실패 원인 : 메뉴를 DB에 등록하고 멍청하게 하드 코딩으로 메뉴ID를 넣었더니 없는 아이디가 나옴 그래서 menu1.getMenuId()로 DB에 저장된 메뉴ID를 넣으니 성공 함
     *
     */

    @DisplayName("단일 주문 생성 성공")
    @Test
    void createOrder() {
        // given
        MenuEntity menu1 = createMenu("아메리카노", 1000, HANDMADE, MenuStatus.SELLING);
        menuRepository.save(menu1);

        OrderDetailRequest orderDetailRequest1 = getOrderDetailRequest(menu1.getMenuId(), 2);

        List<OrderDetailRequest> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetailRequest1);

        OrderCreateRequest request = OrderCreateRequest.builder()
                .orderDetails(orderDetails)
                .build();

        // when
        OrderResponse order = orderServiceImpl.createOrder(request);

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

    @DisplayName("없는 메뉴 주문 시 메뉴를 찾을 수 없습니다.")
    @Test
    void createOrderWhitNotMenuId() {
        // given
        OrderDetailRequest orderDetailRequest1 = getOrderDetailRequest(100L, 2);

        List<OrderDetailRequest> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetailRequest1);

        OrderCreateRequest request = OrderCreateRequest.builder()
                .orderDetails(orderDetails)
                .build();

        // when // then
        assertThatThrownBy(() -> orderServiceImpl.createOrder(request))
                .isInstanceOf(MenuNotFoundException.class)
                .hasMessage("메뉴를 찾을 수 없습니다.");
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