package kiosk.api.menu.service;

import kiosk.api.discount.domain.common.DiscountType;
import kiosk.api.discount.domain.entity.DiscountEntity;
import kiosk.api.discount.repository.DiscountRepository;
import kiosk.api.menu.domain.common.MenuCategory;
import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.menu.domain.dto.response.MenuListResponse;
import kiosk.api.menu.repository.MenuRepository;
import kiosk.api.menu.domain.common.MenuStatus;
import kiosk.api.menu.domain.dto.request.MenuCreateRequest;
import kiosk.api.menu.domain.dto.request.MenuUpdate;
import kiosk.api.menu.domain.dto.response.MenuResponse;
import kiosk.api.menu.service.menuCommand.menuCrate.MenuCreateService;
import kiosk.api.menu.service.menuCommand.menuUpdate.MenuUpdateService;
import kiosk.api.menu.service.menuQuery.menuListQuery.MenuListQuery;
import kiosk.global.exception.handleException.MenuNotFoundException;
import kiosk.global.exception.handleException.validEnumTypeException;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static kiosk.api.menu.domain.common.MenuCategory.BOTTLE;
import static kiosk.api.menu.domain.common.MenuCategory.HANDMADE;
import static kiosk.api.menu.domain.common.MenuStatus.SELLING;
import static kiosk.api.menu.domain.common.MenuStatus.STOP_SELLING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class MenuServiceImplTest {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuServiceImpl menuService;
    @Autowired
    private DiscountRepository discountRepository;

    @DisplayName("MenuId로 메뉴 조회")
    @Test
    void findById() {
        // given
        MenuEntity menu1 = createMenu("아메리카노", 1000, HANDMADE, SELLING);
        menuRepository.save(menu1);

        // when
        MenuEntity byId = menuService.findById(menu1.getMenuId());

        // then
        assertThat(byId).isNotNull();
        assertThat(byId.getMenuPrice()).isEqualTo(1000);
        assertThat(byId.getMenuCategory()).isEqualTo(HANDMADE);
        assertThat(byId.getMenuStatus()).isEqualTo(SELLING);
    }

    @DisplayName("MenuId로 메뉴 조회시 없는 MenuId일 시")
    @Test
    void findByIdWithNotId() {
        // given

        // when // then
        assertThatThrownBy(() -> menuService.findById(100L)).isInstanceOf(MenuNotFoundException.class).hasMessage("메뉴를 찾을 수 없습니다.");
    }

    @DisplayName("MenuId로 할인 포함된 메뉴 조회")
    @Test
    void findByIdWithDiscount() {
        // given
        MenuEntity menu = createMenu("아메리카노", 1000, HANDMADE, SELLING);

        DiscountEntity discount = DiscountEntity.builder()
                .discountCode("DISC10")
                .discountType(DiscountType.PERCENT)
                .discountValue(10)
                .discountStart(LocalDateTime.now().minusDays(1))
                .discountEnd(LocalDateTime.now().plusDays(1))
                .menuEntity(menu)
                .build();

        menu.getDiscountEntity().add(discount);
        menuRepository.save(menu);

        // when
        MenuEntity result = menuService.findByIdWithDiscount(menu.getMenuId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getMenuName()).isEqualTo("아메리카노");

        List<DiscountEntity> discounts = result.getDiscountEntity();
        assertThat(discounts).isNotEmpty();
        assertThat(discounts.get(0).getDiscountCode()).isEqualTo("DISC10");
        assertThat(discounts.get(0).getDiscountValue()).isEqualTo(10);
    }

    public MenuEntity createMenu(String menuName, int menuPrice, MenuCategory menuCategory, MenuStatus menuStatus) {
        return MenuEntity.builder()
                .menuName(menuName)
                .menuPrice(menuPrice)
                .menuCategory(menuCategory)
                .menuStatus(menuStatus)
                .build();
    }
}