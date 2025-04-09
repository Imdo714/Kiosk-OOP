package kiosk.api.menu.domain;

import jakarta.persistence.*;
import kiosk.global.exception.handleException.InvalidEntityException;
import kiosk.global.exception.handleException.validEnumTypeException;
import lombok.*;

import static kiosk.api.menu.domain.MenuStatus.STOP_SELLING;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
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

}
