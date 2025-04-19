package kiosk.api.discount.repository;

import kiosk.api.discount.domain.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {

    List<DiscountEntity> findByMenuEntity_MenuId(Long menuId);

    @Query("SELECT COUNT(d) > 0 " +
            "FROM DiscountEntity d " +
            "WHERE d.menuEntity.menuId = :menuId " +
            "AND ( " +
            "     (:start BETWEEN d.discountStart AND d.discountEnd) " +
            "    OR (:end BETWEEN d.discountStart AND d.discountEnd) " +
            "    OR (d.discountStart BETWEEN :start AND :end) " +
            "    OR (d.discountEnd BETWEEN :start AND :end) " +
            ")")
    boolean existsOverlap(Long menuId, LocalDateTime start, LocalDateTime end);
}
