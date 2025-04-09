package kiosk.api.menu.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kiosk.api.menu.domain.MenuCategory;
import kiosk.api.menu.domain.MenuEntity;
import kiosk.api.menu.domain.MenuStatus;
import kiosk.api.menu.domain.QMenuEntity;
import kiosk.global.exception.handleException.validEnumTypeException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuQueryDslImpl implements MenuQueryDsl {

    private final JPAQueryFactory query;

    public MenuQueryDslImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<MenuEntity> selectMenu(String category, String name, String status) {
        QMenuEntity menu = QMenuEntity.menuEntity;

        return query
                .selectFrom(menu)
                .where(
                        eqCategory(category),
                        containsTitle(name),
                        eqStatus(status)
                )
                .fetch();
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
