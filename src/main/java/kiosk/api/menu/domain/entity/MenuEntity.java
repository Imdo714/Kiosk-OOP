package kiosk.api.menu.domain.entity;

import jakarta.persistence.*;
import kiosk.api.discount.domain.entity.DiscountEntity;
import kiosk.api.menu.domain.common.MenuCategory;
import kiosk.api.menu.domain.common.MenuStatus;
import kiosk.global.exception.handleException.InvalidEntityException;
import kiosk.global.exception.handleException.validEnumTypeException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Builder.Default
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
        if (discountEntity == null || discountEntity.isEmpty()) {
            return this.menuPrice;
        }

        return discountEntity.stream()
                .filter(DiscountEntity::isValidNow)
                .findFirst()
                .map(d -> d.applyDiscount(this.menuPrice))
                .orElse(this.menuPrice);

        // 할인이 여러개 있을 때 중첩 할인 할 때 사용
//        return discountEntity.stream()
//                .filter(DiscountEntity::isValidNow)
//                .reduce(this.menuPrice, // 초기 값
//                        (price, discount) -> discount.applyDiscount(price), // 누적 계산 방식 price: 지금까지 적용된 결과 가격, discount: 다음으로 적용할 할인
//                        (p1, p2) -> p2); // 병렬 병합 방식 그냥 마지막 p2를 사용하겠다 의미
    }

    public Optional<DiscountEntity> getActiveDiscount() { // 현재 시간이 유효한 할인 중 하나만 추출해서 반환
        LocalDateTime now = LocalDateTime.now();
        return discountEntity.stream()
                .filter(d -> d.getDiscountStart().isBefore(now) && d.getDiscountEnd().isAfter(now))
                .findFirst();
    }

    public Long getDiscounId() {
        if (discountEntity == null || discountEntity.isEmpty()) {
            return null;
        }
        return discountEntity.stream()
                .map(DiscountEntity::getDiscountId)
                .findFirst()
                .orElse(null);
    }
}
