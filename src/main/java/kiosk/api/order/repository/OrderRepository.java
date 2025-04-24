package kiosk.api.order.repository;

import kiosk.api.order.domain.entity.OrderEntity;
import kiosk.api.order.domain.internal.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT new kiosk.api.order.domain.internal.OrderDTO(o.orderId, o.orderPrice, o.orderQuantity, o.orderDate) FROM OrderEntity o WHERE o.orderDate >= :start AND o.orderDate < :end")
    List<OrderDTO> findDailyOrder(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new kiosk.api.order.domain.internal.OrderDTO(o.orderId, o.orderPrice, o.orderQuantity, o.orderDate) FROM OrderEntity o WHERE o.orderDate >= :start AND o.orderDate < :end")
    Page<OrderDTO> findRangeDateOrder(LocalDateTime start, LocalDateTime end, Pageable pageable);

}
