package kiosk.api.discount.domain.entity;

import jakarta.persistence.*;
import kiosk.api.discount.domain.common.DiscountType;
import kiosk.api.menu.domain.entity.MenuEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Entity
public class DiscountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id")
    private Long discountId;

    @Column(name = "discount_code")
    private String discountCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType;

    @Column(name = "discount_value")
    private int discountValue;

    @Column(name = "discount_start")
    private LocalDateTime discountStart;

    @Column(name = "discount_end")
    private LocalDateTime discountEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @ToString.Exclude
    private MenuEntity menuEntity;

    public boolean isValidNow() {
        LocalDateTime now = LocalDateTime.now();
        return discountStart.isBefore(now) && discountEnd.isAfter(now);
    }

    public int applyDiscount(int originalPrice) {
        switch (discountType) {
            case PERCENT:
                return originalPrice * (100 - discountValue) / 100;
            case FIXED:
                return Math.max(0, originalPrice - discountValue);
            default:
                return originalPrice;
        }
    }

}
