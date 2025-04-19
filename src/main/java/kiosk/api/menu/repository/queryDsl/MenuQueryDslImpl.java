package kiosk.api.menu.repository.queryDsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kiosk.api.menu.domain.common.MenuCategory;
import kiosk.api.menu.domain.dto.response.MenuResponse;
import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.menu.domain.common.MenuStatus;
import kiosk.api.menu.domain.entity.QMenuEntity;
import kiosk.global.exception.handleException.validEnumTypeException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MenuQueryDslImpl implements MenuQueryDsl {

    private final JPAQueryFactory query;

    public MenuQueryDslImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<MenuResponse> selectMenu(String category, String name, String status) {
        QMenuEntity menu = QMenuEntity.menuEntity;

        List<MenuEntity> result = query
                .selectFrom(menu)
                .where(
                        eqCategory(category),
                        containsTitle(name),
                        eqStatus(status)
                )
                .fetch();

        return result.stream()
                .map(m -> new MenuResponse(
                        m.getMenuId(),
                        m.getMenuName(),
                        m.getMenuPrice(),
                        m.getMenuCategory(),
                        m.getMenuStatus()
                ))
                .collect(Collectors.toList());
    }

    // BooleanExpression은 SQL 쿼리로 번역 가능한 추상적 조건 표현식, Boolean은 자바 메모리에서의 즉시 판단된 논리값
    private BooleanExpression eqCategory(String category) {
        if (category == null){
            return null;
        }

        try { // Enum 타입이기에 예외 처리 해줌
            return QMenuEntity.menuEntity.menuCategory.eq(MenuCategory.valueOf(category.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new validEnumTypeException("존재하지 않는 카테고리입니다.");
        }
    }

    private BooleanExpression eqStatus(String status) {
        if (status == null){
            return null;
        }

        try {
            return QMenuEntity.menuEntity.menuStatus.eq(MenuStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new validEnumTypeException("존재하지 않는 상태 값입니다.");
        }
    }

    private BooleanExpression containsTitle(String name) {
        if (name == null || name.isBlank()){
            return null;
        }

        return QMenuEntity.menuEntity.menuName.containsIgnoreCase(name);
    }

}
