package kiosk.api.order.repository;

import kiosk.api.order.domain.entity.OrderDetailEntity;
import kiosk.api.stats.BestSellingMenuDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

//    @Query("""
//    SELECT od.menuEntity.menuId, od.menuEntity.menuName, SUM(od.orderDetailQuantity) AS totalQuantity
//    FROM OrderDetailEntity od
//    WHERE od.orderEntity.orderDate BETWEEN :startDate AND :endDate
//    GROUP BY od.menuEntity.menuId, od.menuEntity.menuName
//    ORDER BY totalQuantity DESC
//    """)
//    BestSellingMenuDto DailyBestMenuResult(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT new kiosk.api.stats.BestSellingMenuDto(od.menuEntity.menuId, od.menuEntity.menuName, SUM(od.orderDetailQuantity)) " +
            "FROM OrderDetailEntity od " +
            "WHERE od.orderEntity.orderDate >= :startDate AND od.orderEntity.orderDate < :endDate " +
            "GROUP BY od.menuEntity.menuId, od.menuEntity.menuName " +
            "ORDER BY SUM(od.orderDetailQuantity) DESC")
    List<BestSellingMenuDto> DailyBestMenuResult(LocalDateTime startDate, LocalDateTime endDate);

}
