package kiosk.api.menu.domain;

import jakarta.persistence.*;
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
        }
    }

    public void updateMenuStatus(MenuStatus newStatus) {
        if (newStatus != null) {
            this.menuStatus = newStatus;
        }
    }

}
