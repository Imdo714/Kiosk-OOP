package kiosk.api.order.domain.entity;

import jakarta.persistence.*;
import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.global.exception.handleException.InvalidEntityException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Slf4j
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_price")
    private int orderPrice;

    @Column(name = "order_quantity")
    private int orderQuantity;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "orderEntity")
    @Builder.Default
    private List<OrderDetailEntity> orderDetails = new ArrayList<>();

    public static OrderEntity createNow(int orderPrice, int orderQuantity) {
        return OrderEntity.builder()
                .orderPrice(orderPrice)
                .orderQuantity(orderQuantity)
                .orderDate(LocalDateTime.now())
//                .orderDetails(new ArrayList<>()) // new ArrayList<>()로 생성하지 안흥면 null이 들어가서 nullpointerexception 발생
//                그러나 @Builder.Default을 사용해 new ArrayList<>() 값으로 초기화 함 이러면 기본으로 ArrayList가 들어 감
                .build();
    }

    public void updateOrderPrice(int price){
        validPositiveValue(price, "가격은 0보다 커야 합니다.");
        this.orderPrice = price;
    }

    public void checkQuantity(int quantity){
        validPositiveValue(quantity, "주문 수량은 0보다 커야 합니다.");
    }

    private void validPositiveValue(int value, String message) {
        if (value <= 0) {
            throw new InvalidEntityException(message);
        }
    }

    public void addOrderDetail(MenuEntity menu, int quantity) {
        checkQuantity(quantity);

        int discountedPrice = menu.getDiscountedPrice();

        OrderDetailEntity detail = OrderDetailEntity.builder()
                .orderEntity(this)
                .menuEntity(menu)
                .orderDetailQuantity(quantity)
                .build();

        this.orderDetails.add(detail);
        this.orderPrice += discountedPrice * quantity;
        this.orderQuantity += quantity;
    }

}
