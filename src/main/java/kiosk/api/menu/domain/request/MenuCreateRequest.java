package kiosk.api.menu.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kiosk.api.menu.domain.MenuCategory;
import kiosk.api.menu.domain.MenuEntity;
import kiosk.api.menu.domain.MenuStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
public class MenuCreateRequest {

    @NotBlank(message = "메뉴 이름은 필수입니다.")
    private String menuName;

    @Positive(message = "메뉴 가격은 양수여야 합니다.")
    private int menuPrice;

    @NotNull(message = "메뉴 카테고리는 필수입니다.")
    private MenuCategory menuCategory;

    @NotNull(message = "메뉴 상태는 필수입니다.")
    private MenuStatus menuStatus;

    public MenuEntity toEntity() {
        return MenuEntity.builder()
                .menuName(menuName)
                .menuPrice(menuPrice)
                .menuCategory(menuCategory)
                .menuStatus(menuStatus)
                .build();
    }
}
