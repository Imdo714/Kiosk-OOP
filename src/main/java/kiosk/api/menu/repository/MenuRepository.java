package kiosk.api.menu.repository;

import kiosk.api.menu.domain.entity.MenuEntity;
import kiosk.api.menu.repository.queryDsl.MenuQueryDsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long>, MenuQueryDsl {

    @Query("SELECT m FROM MenuEntity m LEFT JOIN FETCH m.discountEntity d WHERE m.menuId = :menuId")
    Optional<MenuEntity> findByIdWithDiscount(@Param("menuId") Long menuId);

}
