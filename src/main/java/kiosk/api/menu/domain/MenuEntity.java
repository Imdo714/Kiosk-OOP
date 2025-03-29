package kiosk.api.menu.domain;

import jakarta.persistence.*;
import kiosk.global.exception.handleException.InvalidEntityException;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
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
        if (newPrice != null && newPrice > 0) {
            this.menuPrice = newPrice;
        } throw new InvalidEntityException("가격이 0이거나 없습니다.");
    }

    public void updateMenuStatus(MenuStatus newStatus) {
        if (newStatus != null) {
            this.menuStatus = newStatus;
        } throw new InvalidEntityException("상태값은 필수입니다.");
    }

}
