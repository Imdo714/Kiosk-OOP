package kiosk.api.menu.domain.entity;

import jakarta.persistence.*;
import kiosk.api.discount.domain.entity.DiscountEntity;
import kiosk.api.menu.domain.common.MenuCategory;
import kiosk.api.menu.domain.common.MenuStatus;
import kiosk.api.order.domain.entity.OrderDetailEntity;
import kiosk.global.exception.handleException.InvalidEntityException;
import kiosk.global.exception.handleException.validEnumTypeException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static kiosk.api.menu.domain.common.MenuStatus.STOP_SELLING;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Slf4j
@ToString
@Entity
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_category")
    private MenuCategory menuCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_status")
    private MenuStatus menuStatus;

    @OneToMany(mappedBy = "menuEntity", cascade = CascadeType.ALL)
    private List<DiscountEntity> discountEntity = new ArrayList<>();

    public void updateMenuPrice(Integer newPrice) {
        if (newPrice == null || newPrice <= 0) {
            throw new InvalidEntityException("가격은 0이상 이거나 필수입니다.");
        } this.menuPrice = newPrice;
    }

    public void updateMenuStatus(MenuStatus newStatus) {
        if (newStatus == null) {
            throw new InvalidEntityException("상태값은 필수입니다.");
        } this.menuStatus = newStatus;
    }

    public void getOrderValid() {
        if(this.menuStatus.equals(STOP_SELLING)){
            throw new validEnumTypeException(this.menuName + "는 판매 중지 상품입니다.");
        }
    }

    public int getDiscountedPrice(){
        return discountEntity.stream()
                .filter(d -> d.isValidNow())
                .findFirst()
                .map(d -> d.applyDiscount(this.menuPrice))
                .orElse(this.menuPrice);

    }
}
