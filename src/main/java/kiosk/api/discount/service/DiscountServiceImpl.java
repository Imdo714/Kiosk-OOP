package kiosk.api.discount.service;

import kiosk.api.discount.domain.dto.request.DiscountRequest;
import kiosk.api.discount.domain.dto.response.DiscountResponse;
import kiosk.api.discount.domain.entity.DiscountEntity;
import kiosk.api.discount.repository.DiscountRepository;
import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.menu.service.MenuService;
import kiosk.global.exception.handleException.InvalidEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final MenuService menuService;

    @Override
    public DiscountResponse createDiscount(DiscountRequest request) {
        MenuEntity byId = menuService.findById(request.getMenuId());

        validateDiscountPeriod(request.getMenuId(), request.getDiscountStart(), request.getDiscountEnd());

        DiscountEntity entity = request.toEntity(byId);
        DiscountEntity save = discountRepository.save(entity);

        return DiscountResponse.of(save);
    }

    public void validateDiscountPeriod(Long menuId, LocalDate start, LocalDate end) {
        LocalDateTime start2 = start.atStartOfDay();
        LocalDateTime end2 = end.plusDays(1).atStartOfDay();

        boolean isOverlap = discountRepository.existsOverlap(menuId, start2, end2);
        if (isOverlap) {
            throw new InvalidEntityException("이미 해당 메뉴에 겹치는 할인 이벤트가 존재합니다.");
        }
    }

}
