package kiosk.api.order.domain;

import jakarta.persistence.*;
import kiosk.api.menu.domain.MenuEntity;
import kiosk.global.exception.handleException.InvalidEntityException;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private MenuEntity menuEntity;

    public void updateOrderPrice(int price){
        validPositiveValue(price, "가격은 0보다 커야 합니다.");
        this.orderPrice = price;
    }

    public void updateOrderQuantity(int quantity){
        validPositiveValue(quantity, "수량은 0보다 커야 합니다.");
        this.orderQuantity = quantity;
    }

    private void validPositiveValue(int value, String message) {
        if (value <= 0) {
            throw new InvalidEntityException(message);
        }
    }

}
