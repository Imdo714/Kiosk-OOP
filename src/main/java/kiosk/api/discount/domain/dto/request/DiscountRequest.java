package kiosk.api.discount.domain.dto.request;

import jakarta.validation.constraints.*;
import kiosk.api.discount.domain.common.DiscountType;
import kiosk.api.discount.domain.entity.DiscountEntity;
import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.global.exception.handleException.InvalidEntityException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@AllArgsConstructor
public class DiscountRequest {

    @NotBlank(message = "할인 코드는 필수입니다.")
    private String discountCode;

    @NotNull(message = "할 타입은 필수입니다.")
    private DiscountType discountType;

    @Positive(message = "할인 값은 0보다 커야 합니다.")
    @NotNull(message = "할인 휼/금액은 필수입니다.")
    private int discountValue;

    @FutureOrPresent(message = "할인 시작일은 현재 또는 미래여야 합니다.")
    private LocalDate discountStart;

    @Future(message = "할인 종료일은 미래 날짜여야 합니다.")
    private LocalDate discountEnd;

    @NotNull(message = "할인 메뉴는 필수입니다.")
    private Long menuId;

    public void isValidDiscountRange(){
        if (discountStart == null || discountEnd == null) {
            throw new InvalidEntityException("할인 시작일과 종료일은 필수입니다.");
        }

        if (discountStart.isAfter(discountEnd)) {
            throw new InvalidEntityException("할인 시작일이 할인 종료일보다 이후일 수 없습니다.");
        }
    }

    public DiscountEntity toEntity(MenuEntity menu){
        isValidDiscountRange();

        return DiscountEntity.builder()
                .discountCode(discountCode)
                .discountType(discountType)
                .discountValue(discountValue)
                .discountStart(discountStart.atStartOfDay())
                .discountEnd(discountEnd.plusDays(1).atStartOfDay())
                .menuEntity(menu)
                .build();
    }

}
