package kiosk.api.order.domain.entity;

import jakarta.persistence.*;
import kiosk.api.discount.domain.common.DiscountType;
import kiosk.api.discount.domain.entity.DiscountEntity;
import kiosk.api.menu.domain.entity.MenuEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Slf4j
@Builder
@Entity
public class OrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @Column(name = "order_detail_quantity")
    private int orderDetailQuantity;

    @Column(name = "order_detail_price")
    private int orderDetailPrice;

    private Long discountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private MenuEntity menuEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;
}
