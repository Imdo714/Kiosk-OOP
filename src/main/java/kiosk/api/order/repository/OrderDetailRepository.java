package kiosk.api.order.repository;

import kiosk.api.order.domain.entity.OrderDetailEntity;
import kiosk.api.stats.bestMenu.domain.dto.response.BestSellingMenuResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

    @Query("SELECT new kiosk.api.stats.bestMenu.domain.dto.response.BestSellingMenuResponse(od.menuEntity.menuId, od.menuEntity.menuName, SUM(od.orderDetailQuantity)) " +
            "FROM OrderDetailEntity od " +
            "WHERE od.orderEntity.orderDate >= :startDate AND od.orderEntity.orderDate < :endDate " +
            "GROUP BY od.menuEntity.menuId, od.menuEntity.menuName " +
            "ORDER BY SUM(od.orderDetailQuantity) DESC LIMIT 3")
    List<BestSellingMenuResponse> DailyBestMenuResult(LocalDateTime startDate, LocalDateTime endDate);

}
